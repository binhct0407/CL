package jp.couplink.app.message;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.couplink.R;
import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.message.model.RoomMessagingObj;
import jp.couplink.app.mocking.TargetUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomMessagingFragment extends Fragment {
    @BindView(R.id.fragment_messaging_PullToLoadView)
    PullToLoadView mPullToLoadView_RoomMessaging;

    Unbinder unbinder;
    //
    RoomMatchingAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;

    public RoomMessagingFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static RoomMessagingFragment newInstance() {
        RoomMessagingFragment fragment = new RoomMessagingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("binh", "onCreate");
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("binh", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_room_messaging, container, false);
        unbinder = ButterKnife.bind(this, view);

        RecyclerView rv = mPullToLoadView_RoomMessaging.getRecyclerView();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        rv.setLayoutManager(layoutManager);

        adapter = new RoomMatchingAdapter(new ArrayList<TargetUser>());
        rv.setAdapter(adapter);
        //
        initializePaginator();
        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(@NotNull View view, int position) {

                RoomChatWebviewFragment fragment = new RoomChatWebviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("user_id", Integer.toString(adapter.getUserAt(position).getId()));
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.message_main_fragment, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        }));
        return view;
    }

    /***********************************************/
    public void initializePaginator() {
        mPullToLoadView_RoomMessaging.isLoadMoreEnabled(true);
        mPullToLoadView_RoomMessaging.setPullCallback(new PullCallback() {
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
        mPullToLoadView_RoomMessaging.initLoad();
    }

    public void loadData(final int page) {
        if (MainActivity.Companion.isLoaded()) {
            isLoading = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Call<RoomMessagingObj> mClUsers = MainActivity.Companion.getMRetrofitAPI().getMessagingRoom();
                    mClUsers.enqueue(new Callback<RoomMessagingObj>() {
                        @Override
                        public void onResponse(Call<RoomMessagingObj> call, Response<RoomMessagingObj> response) {
                            RoomMessagingObj temp = response.body();
                            int size = temp.getData().length;
                            for (int i = 0; i < size; i++) {
                                adapter.add(temp.getData()[i].getTarget_user());
                            }
                            if (size < 20) hasLoadedAll = true;


                        }

                        @Override
                        public void onFailure(Call<RoomMessagingObj> call, Throwable t) {

                        }
                    });

                    //UPDATE PROPETIES
                    try {
                        mPullToLoadView_RoomMessaging.setComplete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isLoading = false;
                    nextPage = page + 1;


                }
            }, 1000);
        }

    }

    /***********************************************/

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("binh", "onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("binh", "onDestroyView");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("binh", "onResume");


    }
}
