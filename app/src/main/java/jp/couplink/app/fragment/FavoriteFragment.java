package jp.couplink.app.fragment;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.couplink.R;
import jp.couplink.app.like.FavoriteListFragment;
import jp.couplink.app.like.FilterActiveDialog;
import jp.couplink.app.like.ReceiveLikeListFragment;
import jp.couplink.app.like.SendLikeListFragment;
import jp.couplink.app.like_thankyou.TabChooseListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFavoriteActionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements TabChooseListener, FilterActiveDialog.DialogButtonListener {
    public static final String TAG = "Tablayout";
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private OnFavoriteActionListener mListener;
    TabChooseListener mTabChooseListener;
    ViewPagerAdapter adapter;
    TextView fragment_favorite_setting;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.d(TAG, "onAttachFragment");
        super.onAttachFragment(childFragment);
        // adapter=new ViewPagerAdapter(getChildFragmentManager());
    }

    public void setTab(int pos) {
        viewPager.setCurrentItem(pos);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        fragment_favorite_setting = (TextView) v.findViewById(R.id.fragment_favorite_setting);
        fragment_favorite_setting.setOnClickListener(new View.OnClickListener() {
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

        int tab_pos = getArguments().getInt("tab_position");
        viewPager.setCurrentItem(tab_pos);

        return v;

    }


    private void showFragmentFavoriteSetting() {
        FragmentManager fm = getFragmentManager();
        FilterActiveDialog dialog = new FilterActiveDialog();
        dialog.setTargetFragment(FavoriteFragment.this, 200);
        dialog.show(fm, "request favorite setting");

    }

    /**
     * Adding custom view to tab
     */
    final void setupTabIcons() {


        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText(" お相手から");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite_tab_selector_0, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(" あなたから");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite_tab_selector_1, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText(" お気に入り");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite_tab_selector_2, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);


    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    final void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ReceiveLikeListFragment(), "ONE");
        adapter.addFrag(new SendLikeListFragment(), "TWO");
        adapter.addFrag(new FavoriteListFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelectListener(int pos) {
        Log.d("binh", "onTabSelectListener");
        viewPager.setCurrentItem(pos);
    }

    @Override
    public void onClickedDialogButton(boolean clicked) {
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
            mListener.onFavoriteAction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof OnFavoriteActionListener) {
            mListener = (OnFavoriteActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFavoriteActionListener");
        }
        if (context instanceof TabChooseListener) {
            mTabChooseListener = (TabChooseListener) context;
            mTabChooseListener.onTabSelectListener(1);
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
    public interface OnFavoriteActionListener {
        // TODO: Update argument type and name
        void onFavoriteAction(Uri uri);
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
