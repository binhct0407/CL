package jp.couplink.app.paginator;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.like.ReceiveLikeAdapter;
import jp.couplink.app.mocking.UserObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BinhTran on 12/26/2017.
 */

public class Paginator_Receive_Like {
    Context context;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    final ReceiveLikeAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;

    public Paginator_Receive_Like(Context context, PullToLoadView pullToLoadView) {
        this.context = context;
        this.pullToLoadView = pullToLoadView;
        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter = new ReceiveLikeAdapter(context, new ArrayList<UserObject>());
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
                if (adapter.getItemCount() >= 1000) hasLoadedAll = true;
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

                    Call<List<UserObject>> mClUsers = MainActivity.Companion.getMRetrofitAPI().getLikeReceives();

                    mClUsers.enqueue(new Callback<List<UserObject>>() {
                        @Override
                        public void onResponse(Call<List<UserObject>> call, Response<List<UserObject>> response) {
                            Log.d("binh", "onResponse");
                            List<UserObject> temp = response.body();
                            int size = temp.size();
                            for (int i = 0; i < size; i++) {
                                adapter.add(temp.get(i));
                            }
                            if (size < 20) hasLoadedAll = true;
                        }

                        @Override
                        public void onFailure(Call<List<UserObject>> call, Throwable t) {

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
