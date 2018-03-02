package jp.couplink.app.paginator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.adapter.SearchUserAdapter;
import jp.couplink.app.database.entities.CLUser;
import jp.couplink.app.database.entities.Data;
import jp.couplink.app.popup.FragmentPopup;
import jp.couplink.app.popup.listener.PopupDetailListener;
import jp.couplink.app.popup.model.Popup;
import jp.couplink.app.retrofit.RetrofitAPI;
import jp.couplink.app.utils.PreferencesConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BinhTran on 12/26/2017.
 */

public class Paginator_Search_Fragment {
    public static boolean loaded_item = false;
    Context context;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    final SearchUserAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    private Map<String, String> data;

    public Paginator_Search_Fragment(Context context, PullToLoadView pullToLoadView) {
        this.context = context;
        this.pullToLoadView = pullToLoadView;
        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        adapter = new SearchUserAdapter(context, new ArrayList<CLUser>());
        rv.setAdapter(adapter);
        /*
        if(MainActivity.Companion.isLoaded())
        {
            getPopupDetail(new PopupDetailListener() {
                @Override
                public void onSuccess(@NotNull Popup mObject) {
                 //call dialog fragment here
                }
            });
        }
        */

    }

    public void initializePaginator() {
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {

                loadData(nextPage);
            }

            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                loadData(1);


            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {


                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();

    }


    private void getPopupDetail(PopupDetailListener mPopupDetailListener) {
        MainActivity.Companion.getMRetrofitAPI().getPopupList().enqueue(new Callback<Popup>() {
            @Override
            public void onResponse(Call<Popup> call, Response<Popup> response) {
                mPopupDetailListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Popup> call, Throwable t) {

            }
        });
    }

    //check filter condition
    final boolean isFilterd() {
        boolean check = false;
        for (int i = 0; i < 10; i++) {
            SharedPreferences pre = context.getSharedPreferences(PreferencesConstant.Companion.getPreferenceName(i), context.MODE_PRIVATE);
            check = pre.getBoolean("filtered", false);
            if (check) {

                break;
            }

        }
        return check;

    }

    ///
    public void loadData(final int page) {

            isLoading = true;
            if (!isFilterd()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Call<Data> mClUsers = MainActivity.Companion.getMRetrofitAPI().getUsers();

                        mClUsers.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                Log.d("binh", "onResponse");
                                loaded_item = true;
                                Data temp = response.body();
                                int size = temp.getData().size();
                                for (int i = 0; i < size; i++) {
                                    adapter.add(temp.getData().get(i));
                                }
                                if (size < 40) hasLoadedAll = true;
                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {

                                System.out.print(t.toString());
                            }
                        });

                        //UPDATE PROPETIES
                        pullToLoadView.setComplete();
                        isLoading = false;
                        nextPage = page + 1;


                    }
                }, 1000);
            } else {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        data = new HashMap<String, String>();
                        for (int i = 0; i < 10; i++)
                            putDataFilter(i);

                        Call<Data> mClUsers = MainActivity.Companion.getMRetrofitAPI().searchUser(data);
                        mClUsers.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                Data temp = response.body();
                                int size = temp.getData().size();
                                for (int i = 0; i < size; i++) {
                                    adapter.add(temp.getData().get(i));
                                }
                                if (size < 40) hasLoadedAll = true;

                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {

                            }
                        });

                        //UPDATE PROPETIES
                        pullToLoadView.setComplete();
                        isLoading = false;
                        nextPage = page + 1;

                    }
                }, 2000);

            }

    }


    /////////


    //put data filter
    final Map<String, String> putDataFilter(int position) {

        SharedPreferences pre = context.getSharedPreferences(PreferencesConstant.Companion.getPreferenceName(position), context.MODE_PRIVATE);
        boolean check_filtered = pre.getBoolean("filtered", false);
        Set<String> set = pre.getStringSet("key", null);


        switch (position) {
            case 0: {
                if (check_filtered) {
                    int start = pre.getInt("yearold_start", 0);
                    int end = pre.getInt("yearold_end", 0);
                    data.put("filter[age]", Integer.toString(start) + ".." + Integer.toString(end));

                }


            }
            break;
            case 3: {
                if (check_filtered) {
                    int start = pre.getInt("filter_height_start", 0);
                    int end = pre.getInt("filter_height_end", 0);
                    data.put("filter[height]", Integer.toString(start) + ".." + Integer.toString(end));

                }

            }
            break;
            case 1: {

                if (check_filtered) {
                    StringBuilder data_put_prefecture = new StringBuilder();

                    for (int j = 0; j < set.size(); j++) {
                        data_put_prefecture.append(new ArrayList<String>(set).get(j) + ",");

                    }
                    data_put_prefecture.deleteCharAt(data_put_prefecture.length() - 1);
                    data.put("filter[prefecture]", data_put_prefecture.toString());
                }

            }
            break;
            case 2: {
                if (check_filtered) {
                    StringBuilder data_put_job = new StringBuilder();
                    for (int j = 0; j < set.size(); j++) {
                        data_put_job.append(new ArrayList<String>(set).get(j) + ",");
                    }
                    data_put_job.deleteCharAt(data_put_job.length() - 1);
                    data.put("filter[job]", data_put_job.toString());

                }
            }
            break;
            case 4: {
                if (check_filtered) {
                    StringBuilder data_put_bodyshape = new StringBuilder();
                    for (int j = 0; j < set.size(); j++) {
                        data_put_bodyshape.append(new ArrayList<String>(set).get(j) + ",");
                    }
                    data_put_bodyshape.deleteCharAt(data_put_bodyshape.length() - 1);
                    data.put("filter[body_shape]", data_put_bodyshape.toString());
                }
            }
            break;
            case 5: {
                if (check_filtered) {
                    StringBuilder data_put_holiday = new StringBuilder();

                    for (int j = 0; j < set.size(); j++) {
                        data_put_holiday.append(new ArrayList<String>(set).get(j) + ",");

                    }
                    data_put_holiday.deleteCharAt(data_put_holiday.length() - 1);
                    data.put("filter[holiday]", data_put_holiday.toString());
                }
            }
            break;
            case 6: {
                if (check_filtered) {
                    StringBuilder data_put_smoking = new StringBuilder();
                    for (int j = 0; j < set.size(); j++) {
                        data_put_smoking.append(new ArrayList<String>(set).get(j) + ",");
                    }
                    data_put_smoking.deleteCharAt(data_put_smoking.length() - 1);
                    data.put("filter[smoking]", data_put_smoking.toString());

                }


            }
            break;
            case 7: {

                if (check_filtered) {
                    StringBuilder data_put_drinking = new StringBuilder();

                    for (int j = 0; j < set.size(); j++) {
                        data_put_drinking.append(new ArrayList<String>(set).get(j) + ",");
                    }
                    data_put_drinking.deleteCharAt(data_put_drinking.length() - 1);
                    data.put("filter[drinking]", data_put_drinking.toString());
                }
            }
            break;
            case 8: {
                if (check_filtered) {

                    for (int j = 0; j < set.size(); j++) {
                        data.put("filter[button_image]:", new ArrayList<String>(set).get(j));
                    }
                }
            }
            break;
            case 9: {
                if (check_filtered) {
                    for (int j = 0; j < set.size(); j++) {
                        data.put("filter[login]", new ArrayList<String>(set).get(j));
                    }
                }
            }
            break;
        }
        return data;

    }


}
