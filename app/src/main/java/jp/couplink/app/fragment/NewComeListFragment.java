package jp.couplink.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srx.widget.PullToLoadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.couplink.R;
import jp.couplink.app.paginator.Paginator_NewComeList_Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewComeListActionListener} interface
 * to handle interaction events.
 * Use the {@link NewComeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewComeListFragment extends Fragment {
    @BindView(R.id.new_come_list)
    PullToLoadView mSearchUserListView;

    Unbinder unbinder;


    private OnNewComeListActionListener mListener;


    public NewComeListFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static NewComeListFragment newInstance() {
        NewComeListFragment fragment = new NewComeListFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_come_list, container, false);
        unbinder = ButterKnife.bind(this, view);


        new Paginator_NewComeList_Fragment(getContext(), mSearchUserListView).initializePaginator();

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNewcomListAction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewComeListActionListener) {
            mListener = (OnNewComeListActionListener) context;
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


    public interface OnNewComeListActionListener {
        // TODO: Update argument type and name
        void onNewcomListAction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("binh", "onResume");


    }
}
