package jp.couplink.app.paginator;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.like.FavoriteLikeAdapter;
import jp.couplink.app.mocking.DataTargetUser;
import jp.couplink.app.mocking.UserObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BinhTran on 12/26/2017.
 */

public class Paginator_Favorite_Like {
    Context context;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    final FavoriteLikeAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;

    public Paginator_Favorite_Like(Context context, PullToLoadView pullToLoadView) {
        this.context = context;
        this.pullToLoadView = pullToLoadView;
        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter = new FavoriteLikeAdapter(context, new ArrayList<UserObject>());
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

                    Call<DataTargetUser> mClUsers = MainActivity.Companion.getMRetrofitAPI().getFavorites();

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

}
