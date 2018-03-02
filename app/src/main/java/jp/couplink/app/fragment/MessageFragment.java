package jp.couplink.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.couplink.R;
import jp.couplink.app.message.EventListFragment;
import jp.couplink.app.message.FilterActiveMessageDialog;
import jp.couplink.app.message.RoomMatchingFragment;
import jp.couplink.app.message.RoomMessagingFragment;
import jp.couplink.app.message.listener.FilterMessagingListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMessageActionListener2} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment implements FilterActiveMessageDialog.DialogButtonListener {
    public static final String TAG = "Tablayout";
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private OnMessageActionListener2 mListener;
    private FilterMessagingListener mFilterMessListener;

    ViewPagerAdapter adapter;


    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.d("Binh", "onAttachFragment");
        super.onAttachFragment(childFragment);
        if (childFragment instanceof FilterMessagingListener) {
            mFilterMessListener = (FilterMessagingListener) childFragment;

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_message_2, container, false);
        TextView txtSetting = (TextView) v.findViewById(R.id.fragment_message_actionbar_settingtxt);
        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentFavoriteSetting();
            }
        });


        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab);
                textView.setTextColor(Color.parseColor("#7acee0"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab);
                textView.setTextColor(Color.parseColor("#ABABAB"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;

    }

    private void showFragmentFavoriteSetting() {
        FragmentManager fm = getFragmentManager();
        FilterActiveMessageDialog dialog = new FilterActiveMessageDialog();
        dialog.setTargetFragment(MessageFragment.this, 201);
        dialog.show(fm, "request favorite setting");
    }

    /**
     * Adding custom view to tab
     */
    @SuppressLint("ResourceType")
    final void setupTabIcons() {
        RelativeLayout layout_customtab = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_message, null);
        TextView tabOne = layout_customtab.findViewById(R.id.tab);
        tabOne.setMaxWidth(350);
        tabOne.setText("マッチング");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.message_tab_selector_0, 0, 0, 0);
        //hide notify
        TextView notify_text = layout_customtab.findViewById(R.id.count_notify);
        notify_text.setVisibility(View.INVISIBLE);
        tabLayout.getTabAt(0).setCustomView(layout_customtab);

        RelativeLayout layout_customtab2 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_message, null);
        TextView tabTwo = layout_customtab2.findViewById(R.id.tab);
        tabTwo.setText("メッセージ中");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.message_tab_selector_1, 0, 0, 0);
        //hide notify
        TextView notify_text_2 = layout_customtab2.findViewById(R.id.count_notify);
        notify_text_2.setVisibility(View.INVISIBLE);
        tabLayout.getTabAt(1).setCustomView(layout_customtab2);

        RelativeLayout layout_customtab3 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_message, null);
        TextView tabThree = layout_customtab3.findViewById(R.id.tab);
        tabThree.setText(" イベント");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.message_tab_selector_2, 0, 0, 0);
        //hide notify
        TextView notify_text_3 = layout_customtab3.findViewById(R.id.count_notify);
        notify_text_3.setVisibility(View.INVISIBLE);
        tabLayout.getTabAt(2).setCustomView(layout_customtab3);


    }


    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    final void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new RoomMatchingFragment(), "ONE");
        adapter.addFrag(new RoomMessagingFragment(), "TWO");
        adapter.addFrag(new EventListFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClickedDialogButton(boolean clicked) {
        mFilterMessListener.onFilteredMessaging(false);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        Log.d(TAG, "onResume");
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMessageAction2(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof OnMessageActionListener2) {
            mListener = (OnMessageActionListener2) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFavoriteActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMessageActionListener2 {
        // TODO: Update argument type and name
        void onMessageAction2(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
}
