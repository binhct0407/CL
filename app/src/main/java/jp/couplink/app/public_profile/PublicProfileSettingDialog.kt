package jp.couplink.app.public_profile

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.adapter.SearchUserAdapter
import jp.couplink.app.database.entities.CLUser
import jp.couplink.app.public_profile.model.FavoriteUserObj
import jp.couplink.app.public_profile.model.LikeResponse
import kotlinx.android.synthetic.main.public_profile_setting_dialog.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by BinhTran on 12/21/2017.
 */

class PublicProfileSettingDialog : DialogFragment() {


    internal var check_unlike = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.public_profile_setting_dialog, container, false)
        ButterKnife.bind(this, layout)




        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        publicprofile_setting_exit.setOnClickListener(View.OnClickListener { dismissDialog() })
        publicprofile_setting_violate.setOnClickListener(View.OnClickListener { display_violate() })
        publicprofile_setting_favorite!!.setOnClickListener(View.OnClickListener { unlike() })

    }

    fun dismissDialog() {
        dismiss()

    }

    fun display_violate() {
        val fragment = PublicProfileViolateFragment()
        fragmentManager!!.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit()
        dismiss()
    }

    fun doreport() {
        val fragment = PublicProfileReportFragment()
        fragmentManager!!.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit()
        dismiss()
    }

    fun unlike() {
        check_unlike = !check_unlike
        if (check_unlike) {

            request_favorite()
            publicprofile_setting_favorite!!.setBackgroundResource(R.drawable.rounded_button_publicprofile_setting_gray)
            publicprofile_setting_favorite!!.text = "お気に入りから削除する"
        } else {
            request_delete_favorite()
            publicprofile_setting_favorite!!.setBackgroundResource(R.drawable.rounded_button_publicprofile_setting_red)
            publicprofile_setting_favorite!!.text = "お気に入りにする"

        }

    }

    private fun request_favorite() {


        var current_object: CLUser? = SearchUserAdapter.Companion.current_user_obj
        var mFavoriteUserObj: FavoriteUserObj = FavoriteUserObj()
        mFavoriteUserObj.target_user_id = current_object!!.id.toString()


        var mClUsers: Call<Any> = MainActivity.Companion.mRetrofitAPI!!.favoritetoID(mFavoriteUserObj)
        mClUsers.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val temp = response.body()
                var st = response.toString()
                Log.d("binh", "log is " + st)


            }

            override fun onFailure(call: Call<Any>, t: Throwable) {


            }
        })

    }

    private fun request_delete_favorite() {

        var current_object: CLUser? = SearchUserAdapter.Companion.current_user_obj


        var mClUsers: Call<Any> = MainActivity.Companion.mRetrofitAPI!!.deleteFavorite(current_object!!.id.toString())
        mClUsers.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val temp = response.body()
                var st = response.toString()
                Log.d("binh", "log is " + st)


            }

            override fun onFailure(call: Call<Any>, t: Throwable) {


            }
        })

    }


}
