package jp.couplink.app.paginator;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.adapter.NewcomerListAdapter;
import jp.couplink.app.database.entities.CLUser;
import jp.couplink.app.database.entities.Data;
import jp.couplink.app.retrofit.NewcomerAPI;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BinhTran on 12/26/2017.
 */

public class Paginator_NewComeList_Fragment {
    Context context;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    final NewcomerListAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    private Map<String, String> data;
    public static final String AUTH_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.Qpc9pcMaMoEV8sYFNclnCwxuvvqULfeTLo-f18UByww";

    public Paginator_NewComeList_Fragment(Context context, PullToLoadView pullToLoadView) {
        this.context = context;
        this.pullToLoadView = pullToLoadView;
        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        adapter = new NewcomerListAdapter(context, new ArrayList<CLUser>());
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

                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .header("Authorization", "Bearer " + MainActivity.Companion.getAuth_token())
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    }).build();
                    Map<String, String> data = new HashMap<>();
                    Call<Data> mClUsers = MainActivity.Companion.getMRetrofitAPI().searchUser(data);
                    mClUsers.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            Data temp = response.body();
                            int size = temp.getData().size();
                            for (int i = 0; i < size; i++) {
                                adapter.add(temp.getData().get(i));
                            }
                            if (size < 20) hasLoadedAll = true;


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
            }, 1000);


        }
    }


}
