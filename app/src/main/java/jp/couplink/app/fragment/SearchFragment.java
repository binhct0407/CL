package jp.couplink.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.srx.widget.PullToLoadView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.couplink.R;
import jp.couplink.app.activity.FilterFriendActivity;
import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.database.entities.CLUser;
import jp.couplink.app.mocking.UserObject;
import jp.couplink.app.paginator.Paginator_Search_Fragment;
import jp.couplink.app.popup.FragmentPopup;
import jp.couplink.app.popup.listener.PopupDetailListener;
import jp.couplink.app.popup.model.Popup;
import jp.couplink.app.utils.PopupUtils;
import jp.couplink.app.utils.PreferencesConstant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSearchActionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    public static Popup tempPopup;
    private Toolbar mToolbar;
    private TextView mToolbar_search;
    @BindView(R.id.searchfragment_user_list)
    PullToLoadView mSearchUserListView;

    Unbinder unbinder;


    private OnSearchActionListener mListener;

    private List<CLUser> mUserList = new ArrayList<>();
    public static List<CLUser> mlist;


    public SearchFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("binh", "onCreate");
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("binh", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        unbinder = ButterKnife.bind(this, view);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar_search = mToolbar.findViewById(R.id.searchfragment_actionbar_txt_search);
        if (isFilterd()) {
            mToolbar_search.setTextColor(Color.parseColor("#e0585a"));
            mToolbar_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_red_600_24dp
                    , 0, 0, 0);
        }
        mToolbar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterFriendActivity.class);
                startActivity(intent);
            }
        });

        new Paginator_Search_Fragment(getContext(), mSearchUserListView).initializePaginator();
        //start get popup
        ////////////////////////////////////////////////
        /*
        if (MainActivity.Companion.isLoaded()) {
            getPopupDetail(new PopupDetailListener() {
                @Override
                public void onSuccess(@NotNull Popup mObject) {

                    ArrayList<DialogFragment> mListDialog;
                    mListDialog = PopupUtils.Companion.getListDialogFragments(mObject);
                    int size_popup = mListDialog.size();
                    if (Paginator_Search_Fragment.loaded_item) {

                        for (int i = size_popup - 1; i >= 0; i--) {
                            FragmentManager fragmentManager = getFragmentManager();
                            mListDialog.get(i).show(fragmentManager, "popup");

                        }
                    }
                }
            });
        }
        */
        //////////////////////////////////////
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSearchAction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchActionListener) {
            mListener = (OnSearchActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFavoriteActionListener");
        }
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


    final boolean isFilterd() {
        boolean check = false;
        for (int i = 0; i < 10; i++) {
            SharedPreferences pre = getActivity().getSharedPreferences(PreferencesConstant.Companion.getPreferenceName(i), getActivity().MODE_PRIVATE);
            check = pre.getBoolean("filtered", false);
            if (check) {

                break;
            }

        }
        return check;

    }


    public interface OnSearchActionListener {
        // TODO: Update argument type and name
        void onSearchAction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("binh", "onResume");
        mlist = mUserList;
        if (isFilterd()) {
            mToolbar_search.setText("設定中");
            mToolbar_search.setTextColor(getResources().getColor(R.color.colorNoticeText));
            mToolbar_search.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_red_600_24dp
                    , 0, 0, 0);
        }


    }

    private void getPopupDetail(PopupDetailListener mPopupDetailListener) {
        Call<Popup> mClUsers = MainActivity.Companion.getMRetrofitAPI().getPopupList();

        mClUsers.enqueue(new Callback<Popup>() {
            @Override
            public void onResponse(Call<Popup> call, Response<Popup> response) {
                Log.d("binh", "onResponse");
                Popup temp = response.body();
                mPopupDetailListener.onSuccess(temp);


            }

            @Override
            public void onFailure(Call<Popup> call, Throwable t) {

                Log.d("binh", "onFailure roi " + t.toString());
                System.out.print(t.toString());
            }
        });
    }

}
