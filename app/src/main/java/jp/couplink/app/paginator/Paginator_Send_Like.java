package jp.couplink.app.paginator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.like.FilterActiveDialog;
import jp.couplink.app.mocking.DataTargetUser;
import jp.couplink.app.mocking.UserObject;
import jp.couplink.app.like.SendLikeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BinhTran on 12/26/2017.
 */

public class Paginator_Send_Like implements FilterActiveDialog.DialogButtonListener {
    Context context;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    final SendLikeAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;

    public Paginator_Send_Like(Context context, PullToLoadView pullToLoadView) {
        this.context = context;
        this.pullToLoadView = pullToLoadView;
        //RECYCLERVIEW
        new FilterActiveDialog().setListener(this);
        RecyclerView rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter = new SendLikeAdapter(context, new ArrayList<UserObject>());
        rv.setAdapter(adapter);
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

    ///
    public void loadData(final int page) {
        if (MainActivity.Companion.isLoaded()) {
            isLoading = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences pre = context.getSharedPreferences("filter_active", Context.MODE_PRIVATE);
                    boolean is_filteractive = pre.getBoolean("is_filteractive", false);

                    Map<String, String> data = new HashMap<>();
                    if (is_filteractive) data.put("filter[active]", "1");
                    else data.put("filter[active]", "0");

                    Call<DataTargetUser> mClUsers = MainActivity.Companion.getMRetrofitAPI().getLikeSents(data);

                    mClUsers.enqueue(new Callback<DataTargetUser>() {
                        @Override
                        public void onResponse(Call<DataTargetUser> call, Response<DataTargetUser> response) {
                            Log.d("binh", "onResponse");
                            DataTargetUser temp = response.body();
                            int size = temp.getData().size();
                            for (int i = 0; i < size; i++) {
                                adapter.add(temp.getData().get(i));
                            }
                            if (size < 20) hasLoadedAll = true;
                        }

                        @Override
                        public void onFailure(Call<DataTargetUser> call, Throwable t) {

                            Log.d("binh", "onFailure roi " + t.toString());
                            System.out.print(t.toString());
                        }
                    });

                    //UPDATE PROPETIES
                    pullToLoadView.setComplete();
                    isLoading = false;
                    nextPage = page + 1;


                }
            }, 1000);

        }
    }

    @Override
    public void onClickedDialogButton(boolean clicked) {
        Toast.makeText(context, "filter roi", Toast.LENGTH_SHORT).show();
    }

}
