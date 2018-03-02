package jp.couplink.app.activity

import android.annotation.SuppressLint
import android.app.DialogFragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jp.couplink.R
import jp.couplink.app.fragment.*
import jp.couplink.app.fragment.dialog.LoadingDialogFragment
import jp.couplink.app.fragment.dialog.ProgressDialogFragment
import jp.couplink.app.listener.SettingChooseListener
import jp.couplink.app.popup.listener.PopupDetailListener
import jp.couplink.app.popup.model.Popup
import jp.couplink.app.retrofit.RetrofitAPI
import jp.couplink.app.retrofit.model.AuthObject
import jp.couplink.app.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchFragment.OnSearchActionListener, FavoriteFragment.OnFavoriteActionListener, NewComeListFragment.OnNewComeListActionListener, MessageFragment.OnMessageActionListener2, NetworkErrorFragment.NetworkErrorFragmentInterface.onClickListener, SettingFragment.OnSettingActionListener, SettingChooseListener, SettingWebviewFragment.OnSettingWebViewActionListener {
    override fun OnSettingWebViewAction(uri: Uri) {
    }

    var fragment: Fragment? = null


    companion object {
        val TAG: String = "COUPLINK"
        var auth_token: String? = null
        var mRetrofitAPI: RetrofitAPI? = null
        var tempPopup: Popup? = null
        var isLoaded: Boolean = false
        var tem: Popup? = null
    }


    override fun onSelectedSetting(num: Int) {
        if (num == 6) {
            var mBundle: Bundle? = Bundle()
            mBundle!!.putInt("tab_position", 2)


            main_navigation.selectedItemId = R.id.navigation_dashboard
            fragment!!.arguments = mBundle
        }

        if (num == 5) {
            var mBundle: Bundle? = Bundle()
            mBundle!!.putInt("tab_position", 1)


            main_navigation.selectedItemId = R.id.navigation_dashboard
            fragment!!.arguments = mBundle
        } else
            if (num == 4) {
                main_navigation.selectedItemId = R.id.navigation_dashboard
            }

    }


    private var mNetworkErrorDialog: NetworkErrorFragment? = null
    private var mLoadingDialog: ProgressDialogFragment? = null
    private val LOADING_DISPLAYED_TIME = 10 * 1000L

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->


        when (item.itemId) {
            R.id.navigation_home -> fragment = SearchFragment.newInstance()

            R.id.navigation_dashboard -> fragment = FavoriteFragment.newInstance()

            R.id.navigation_new -> fragment = NewComeListFragment.newInstance()


            R.id.navigation_message -> fragment = MessageFragment.newInstance()

            R.id.navigation_setting -> fragment = SettingWebviewFragment.newInstance()
        }


        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .commit()
            return@OnNavigationItemSelectedListener true
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get token
        val pre = getSharedPreferences("cookie_pre", Context.MODE_PRIVATE)
        var cookie: String = pre.getString("login_cookie", "")
        val retrofit = Retrofit.Builder().baseUrl(UrlManager.URL_BASE).addConverterFactory(GsonConverterFactory.create()).build()
        val authAPI = retrofit.create(RetrofitAPI::class.java)
        val authObject = authAPI.getAuthToken(cookie)
        authObject.enqueue(object : Callback<AuthObject> {
            override fun onResponse(call: Call<AuthObject>?, response: Response<AuthObject>?) {
                var temp: AuthObject? = response!!.body()
                auth_token = temp!!.access_token
                val client = OkHttpClient.Builder().addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                            .header("Authorization", "Bearer " + auth_token!!)
                            .build()
                    chain.proceed(newRequest)
                }.build()
                mRetrofitAPI = Retrofit.Builder().client(client).baseUrl(UrlManager.URL_BASE).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitAPI::class.java)
                isLoaded = true

                //get popup detail

                getPopupDetail(object : PopupDetailListener {
                    override fun onSuccess(mObject: Popup) {
                        Log.d("binh", "object is " + mObject)

                        //load like thank you
                        /*
                        var fragment=PopupTypeLinkUtils.getPopupTypeLink(mObject)
                        fragmentManager.beginTransaction().replace(R.id.fragment_search_main, fragment).addToBackStack(null).commit()
*/
                        //load poup

                        var mListDialog: ArrayList<DialogFragment>
                        mListDialog = PopupUtils.getListDialogFragments(mObject)


                        for (i in mListDialog.reversed()) {


                            i.show(fragmentManager!!, "popup")

                        }


                       //  mListDialog.get(0).show(fragmentManager!!,"tutorial")
                        // fragmentManager.beginTransaction().replace(R.id.fragment_search_main, mListDialog.get(0)).addToBackStack(null).commit()


                        //mListDialog.get(0).show(fragmentManager!!, "popup")

                        /*
                         val size:Int=mObject!!.data!!.size
                        for (i in 0..size-1)
                        {
                            if(mObject!!.data!![i]!!.type.equals("link"))
                            {
                                var bundle:Bundle?= Bundle()
                                bundle!!.putString("typelink_url", mObject!!.data!![i].data!!.url)
                                var fragment=FragmentPopupTypeLink()
                                fragment.arguments=bundle
                                fragmentManager.beginTransaction().replace(R.id.fragment_search_main, fragment).addToBackStack(null).commit()

                            }
                        }
                        */


                        //fake for type=modal
                        /*
                        var bundle: Bundle? = Bundle()
                        bundle!!.putParcelable("popupdata", mObject)
                        val fragment = FragmentPopup()
                        fragment.arguments = bundle
                        fragment.show(fragmentManager!!, "show popup")
                        */

                    }
                })


            }

            override fun onFailure(call: Call<AuthObject>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }
        })
        checkInitNetwork()


    }


    private fun checkInitNetwork() {
        if (NetworkCheck.checkNetWork(this)) {
            init()
        } else {
            openNetworkErrorFragment(NetworkErrorFragment.AccessCode.CheckInitNetwork)
            CLAlert.notify(this, getString(R.string.network_error_meg2))
            //  alert(getString(R.string.network_error_meg2));
        }
    }

    private fun init() {

        disableShiftMode(main_navigation)
        main_navigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_navigation!!.selectedItemId = R.id.navigation_home
    }

    private fun openNetworkErrorFragment(accessCode: NetworkErrorFragment.AccessCode, message: String = "") {
        setTheme(R.style.NetworkErrorDialogTheme)
        if (mNetworkErrorDialog == null) {
            mNetworkErrorDialog = NetworkErrorFragment()

            val bundle = Bundle()
            bundle.putInt("accessCode", accessCode.int)
            bundle.putString("message", message)
            mNetworkErrorDialog!!.arguments = bundle

            val ft = supportFragmentManager.beginTransaction()
            ft.add(mNetworkErrorDialog, null)
            ft.commitAllowingStateLoss()

        }
    }


    override fun onResume() {
        super.onResume()


    }

    override fun onSearchAction(uri: Uri) {

    }


    @SuppressLint("RestrictedApi")
    private fun disableShiftMode(view: BottomNavigationView?) {
        val menuView = view!!.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalAccessException) {
        }

    }


    override fun onMessageAction2(uri: Uri) {

    }


    override fun onReAccessButtonClick() {
        openLoadingDialog()
    }

    private fun openLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialogFragment.instance
            val ft = supportFragmentManager.beginTransaction()
            ft.add(mLoadingDialog, null)
            ft.commitAllowingStateLoss()
            val handler = Handler()
            handler.postDelayed({
                dismissLoadingDialog()
                if (mNetworkErrorDialog != null) {
                    CLAlert.notify(this@MainActivity, getString(R.string.network_error_meg))
                }
            }, LOADING_DISPLAYED_TIME)
        }
    }

    private fun dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog!!.dismissAllowingStateLoss()
            mLoadingDialog = null
        }
    }

    override fun onFavoriteAction(uri: Uri) {

    }

    override fun onNewcomListAction(uri: Uri) {

    }

    override fun OnSettingAction(uri: Uri) {

    }

    private fun getPopupDetail(mPopupDetailListener: PopupDetailListener) {
        val mClUsers = mRetrofitAPI!!.getPopupList()

        mClUsers.enqueue(object : Callback<Popup> {
            override fun onResponse(call: Call<Popup>, response: Response<Popup>) {
                Log.d("binh", "onResponse")
                val temp = response.body()

                mPopupDetailListener.onSuccess(temp!!)


            }

            override fun onFailure(call: Call<Popup>, t: Throwable) {

                Log.d("binh", "onFailure roi " + t.toString())
                print(t.toString())
            }
        })
    }

}
