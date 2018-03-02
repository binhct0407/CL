package jp.couplink.app.message;

import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.couplink.R;
import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.message.listener.FilterMessagingListener;
import jp.couplink.app.message.model.RoomMatchedObj;
import jp.couplink.app.mocking.TargetUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomMatchingFragment extends Fragment implements FilterMessagingListener {
    @BindView(R.id.fragment_matched_user_PullToLoadView)
    PullToLoadView mPullToLoadView_MatchedUser;
    @BindView(R.id.layout_empty_Matched)
    LinearLayout layout_empty_Matched;
    private OnRoomMatchingAction mListener;

    Unbinder unbinder;
    //
    RoomMatchingAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;

    //


    public RoomMatchingFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static RoomMatchingFragment newInstance() {
        RoomMatchingFragment fragment = new RoomMatchingFragment();
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
        View view = inflater.inflate(R.layout.fragment_matched_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        layout_empty_Matched.setVisibility(View.GONE);
        //setup for recyclerView
        RecyclerView rv = mPullToLoadView_MatchedUser.getRecyclerView();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setLayoutManager(layoutManager);
        adapter = new RoomMatchingAdapter(new ArrayList<TargetUser>());
        rv.setAdapter(adapter);
        //

        initializePaginator();

        return view;
    }


    /*********************************/
    public void initializePaginator() {
        mPullToLoadView_MatchedUser.isLoadMoreEnabled(true);
        mPullToLoadView_MatchedUser.setPullCallback(new PullCallback() {
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
        mPullToLoadView_MatchedUser.initLoad();
    }

    private void loadEmptyWebView() {

    }


    public void loadData(final int page) {
        if (MainActivity.Companion.isLoaded()) {
            isLoading = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Call<RoomMatchedObj> mClUsers = MainActivity.Companion.getMRetrofitAPI().getMatchedRoom();
                    mClUsers.enqueue(new Callback<RoomMatchedObj>() {
                        @Override
                        public void onResponse(Call<RoomMatchedObj> call, Response<RoomMatchedObj> response) {
                            RoomMatchedObj temp = response.body();
                            int size = temp.getData().length;
                            if (size == 0 && layout_empty_Matched != null) {
                                layout_empty_Matched.setVisibility(View.VISIBLE);

                            }
                            for (int i = 0; i < size; i++) {
                                adapter.add(temp.getData()[i].getTarget_user());
                            }
                            if (size < 20) hasLoadedAll = true;


                        }

                        @Override
                        public void onFailure(Call<RoomMatchedObj> call, Throwable t) {

                        }
                    });

                    //UPDATE PROPETIES
                    try {
                        mPullToLoadView_MatchedUser.setComplete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    isLoading = false;
                    nextPage = page + 1;


                }
            }, 1000);
        }

    }

    /*********************************/

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onFilteredMessaging(boolean check) {
        Toast.makeText(getContext(), "OK nhe", Toast.LENGTH_SHORT).show();
    }

    public interface OnRoomMatchingAction {
        // TODO: Update argument type and name
        void OnRoomMatchingAction(Uri uri);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("binh", "onDetach");
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("binh", "onDestroyView");
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("binh", "onResume");


    }
}
