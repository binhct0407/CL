package jp.couplink.app.like;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srx.widget.PullToLoadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.couplink.R;
import jp.couplink.app.paginator.Paginator_Receive_Like;

/**
 * Created by BinhTran on 12/25/2017.
 */

public class ReceiveLikeListFragment extends Fragment {
    @BindView(R.id.receive_like_list)
    PullToLoadView receive_like_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_receive_like_list, container, false);
        ButterKnife.bind(this, v);

        new Paginator_Receive_Like(getContext(), receive_like_list).initializePaginator();


        return v;
    }
}
