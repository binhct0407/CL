package jp.couplink.app.fragment

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import jp.couplink.R
import jp.couplink.app.constants.Constants
import jp.couplink.app.like_thankyou.TabChooseListener
import jp.couplink.app.listener.SettingChooseListener
import jp.couplink.app.purchase.coin.FragmentCoinPage
import jp.couplink.app.purchase.membership.FragmentMembership
import jp.couplink.app.setting.CoinPurchaseFragment
import jp.couplink.app.setting.SettingGridAdapter
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {
    var activity: Activity? = null
    private var mListener: OnSettingActionListener? = null
    private var mSettingChooseListner: SettingChooseListener? = null
    private var mTabChooseListener: TabChooseListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SettingGridAdapter(context!!, Constants.title_setting, Constants.images_setting)
        fragment_setting_gridview!!.adapter = adapter
        fragment_setting_gridview!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                val fragment: CoinPurchaseFragment
                fragment = CoinPurchaseFragment()

                fragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_setting_contain_setting, fragment)
                        .addToBackStack(null)
                        .commit()
            }
            if (position == 1) {

                var child_fragment: FragmentMembership
                child_fragment = FragmentMembership()

                fragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_setting_contain_setting, child_fragment)
                        .addToBackStack(null)
                        .commit()
                /*
                var intent=Intent(context,FragmentMembership::class.java)
                startActivity(intent)
                */
            }

                if (position == 5) {

                    mSettingChooseListner!!.onSelectedSetting(5)
                }
            if (position == 4) {
                //mSettingChooseListner!!.onSelectedSetting(4)
                //mTabChooseListener!!.onTabSelectListener(1)
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.OnSettingAction(uri)
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSettingActionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFavoriteActionListener")
        }
        if (context is SettingChooseListener) {
            mSettingChooseListner = context

        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        mSettingChooseListner = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnSettingActionListener {
        // TODO: Update argument type and name
        fun OnSettingAction(uri: Uri)
    }

    companion object {

        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }


    }

    override fun onAttachFragment(childFragment: android.support.v4.app.Fragment?) {
        super.onAttachFragment(childFragment)
        if (childFragment is TabChooseListener)
            mTabChooseListener = childFragment
    }
}
