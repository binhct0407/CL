package jp.couplink.app.public_profile

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.adapter.GroupBasicInforAdapter
import jp.couplink.app.adapter.PublicProfileCommonAdapter
import jp.couplink.app.adapter.PublicProfileSubImageAdapter
import jp.couplink.app.adapter.SearchUserAdapter
import jp.couplink.app.database.entities.CLUser
import jp.couplink.app.database.entities.PublicProfileObj
import jp.couplink.app.fragment.SearchFragment
import jp.couplink.app.mocking.PublicProfile_Mocking_Constant
import jp.couplink.app.public_profile.model.LikeResponse
import jp.couplink.app.public_profile.model.LikeUserObj
import jp.couplink.app.retrofit.model.DataPublic
import jp.couplink.app.public_profile.listener.UserDetailListener
import jp.couplink.app.utils.GlideImageConstant
import jp.couplink.app.utils.ResourceUtils
import jp.couplink.app.utils.RoundedCornersTransformation
import jp.couplink.app.utils.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_public_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by BinhTran on 12/18/2017.
 */

class PublicProfileFragment : Fragment(), PublicProfileRequestPhotoDialog.DialogButtonListener, DialogFragmentListener {

    var mConText: Context? = context
    var mPublicProfileObj: PublicProfileObj? = null
    var smallpic_path = ArrayList<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_public_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //prepareMock()
        parseViewfromSearch()
        getUserDetailObj(object : UserDetailListener {
            override fun onSuccess(mObject: PublicProfileObj) {
                mPublicProfileObj = mObject
                if (mPublicProfileObj != null)
                    updateView(mPublicProfileObj!!)

            }
        })
        fragment_publicprofile_requestQuestion_txt.setOnClickListener(View.OnClickListener { requestQuestion() })

    }

    private fun requestQuestion() {
        var fragment = QuestionWebviewFragment()
        var bundle: Bundle? = Bundle()
        bundle!!.putInt("currentProfileId", mPublicProfileObj!!.id)
        fragment!!.arguments = bundle



        getFragmentManager()!!.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }


    private fun parseViewfromSearch() {
        var current_obj: CLUser? = SearchUserAdapter.Companion.current_user_obj

        //set name for actionbar
        fragment_public_profile_barName.text = current_obj!!.name!!

        //set name,age and prefecture
        fragment_public_profile_name_age.text = current_obj.name!! + " " + current_obj.age.toString() + " " + current_obj.residence_prefecture_name

        //set for self_introduction
        fragment_public_profile_txt_self_introduction!!.text = current_obj.self_introduction


        //set button_image Main
        Glide.with(context).load(current_obj!!.top_image!!.original)
                .apply(RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(context, GlideImageConstant.sCorner, GlideImageConstant.sColor, GlideImageConstant.sBorder))).into(fragment_public_profile_img_main!!)
        fragment_public_profile_scrollview.setBackgroundResource(ResourceUtils.getImage(context!!, current_obj.bg_theme.toString()))
    }


    private fun updateView(mTempPublicProfileObj: PublicProfileObj) {

        //set last active at
        fragment_public_profile_last_active_at!!.text = mTempPublicProfileObj.last_active_at

        //set same event_with me
        if (mTempPublicProfileObj.same_event_with_me)
            fragment_public_profile_same_event_with_me.text = "イベント参加者"


        //set for basic information
        var basic_name: MutableList<String> = mutableListOf()
        var detail: MutableList<String> = mutableListOf()
        for (i in 0..mTempPublicProfileObj.basic_information.size - 1) {
            basic_name.add(mTempPublicProfileObj.basic_information.get(i).label!!)
            detail.add(mTempPublicProfileObj.basic_information.get(i).data!!)


        }

        val adapter_basic = GroupBasicInforAdapter(basic_name, detail)
        val basic_manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragment_public_profile_group_basic_infor!!.layoutManager = basic_manager
        fragment_public_profile_group_basic_infor!!.addItemDecoration(SimpleDividerItemDecoration(
                context
        ))
        fragment_public_profile_group_basic_infor!!.adapter = adapter_basic


        //set for work_related
        var group_work: MutableList<String> = mutableListOf()
        var detail_work: MutableList<String> = mutableListOf()
        for (i in 0..mTempPublicProfileObj.work_related.size - 1) {
            group_work.add(mTempPublicProfileObj.work_related.get(i).label!!)
            detail_work.add(mTempPublicProfileObj.work_related.get(i).data!!)


        }
        val adapter_work = GroupBasicInforAdapter(group_work, detail_work)
        val work_manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragment_public_profile_group_work_infor!!.layoutManager = work_manager
        fragment_public_profile_group_work_infor!!.addItemDecoration(SimpleDividerItemDecoration(
                context
        ))
        fragment_public_profile_group_work_infor!!.adapter = adapter_work


        //set for hobby
        var group_hobby: MutableList<String> = mutableListOf()
        var detail_hobby: MutableList<String> = mutableListOf()
        for (i in 0..mTempPublicProfileObj.hobby_related.size - 1) {
            group_hobby.add(mTempPublicProfileObj.hobby_related.get(i).label!!)
            detail_hobby.add(mTempPublicProfileObj.hobby_related.get(i).data!!)

        }
        val adapter_hobby = GroupBasicInforAdapter(group_hobby, detail_hobby)
        val hobby_manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragment_public_profile_group_hobby_infor!!.layoutManager = hobby_manager
        fragment_public_profile_group_hobby_infor!!.addItemDecoration(SimpleDividerItemDecoration(
                context
        ))
        fragment_public_profile_group_hobby_infor!!.adapter = adapter_hobby

        //set for relation_related
        var group_relation: MutableList<String> = mutableListOf()
        var detail_relation: MutableList<String> = mutableListOf()
        for (i in 0..mTempPublicProfileObj.relationship_related.size - 1) {
            group_relation.add(mTempPublicProfileObj.relationship_related.get(i).label!!)
            detail_relation.add(mTempPublicProfileObj.relationship_related.get(i).data!!)
        }
        val adapter_mariatal = GroupBasicInforAdapter(group_relation, detail_relation)
        val mariatal_manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        fragment_public_profile_group_marital_infor!!.layoutManager = mariatal_manager
        fragment_public_profile_group_marital_infor!!.addItemDecoration(SimpleDividerItemDecoration(
                context
        ))
        fragment_public_profile_group_marital_infor!!.adapter = adapter_mariatal

        //set up common point
        if (mTempPublicProfileObj.images.size < 4) {

        } else {
            fragment_public_profile_btn_request_photo.visibility = View.GONE
        }
        var images_path: MutableList<String> = mutableListOf()
        for (i in 0..mTempPublicProfileObj.common_points.size - 1) {
            images_path.add(mTempPublicProfileObj.common_points.get(i).name.toString())
        }


        val commonAdapter = PublicProfileCommonAdapter(images_path)
        val commonManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        fragment_public_profile_group_common_infor!!.layoutManager = commonManager
        fragment_public_profile_group_common_infor!!.adapter = commonAdapter

        fragment_public_profile_btn_request_photo.setOnClickListener(View.OnClickListener { requested_photo() })
        fragment_public_profile_btn_like.setOnClickListener(View.OnClickListener { likeprofile() })
        fragment_public_profile_back.setOnClickListener(View.OnClickListener { back() })
        fragment_public_profile_setting.setOnClickListener(View.OnClickListener { displaySetting() })


        //set sub button_image
        var subimage_path: MutableList<String> = mutableListOf()
        for (i in 0..mTempPublicProfileObj.images.size - 1) {
            subimage_path.add(mTempPublicProfileObj.images.get(i).small!!)
        }
        val adapter = PublicProfileSubImageAdapter(subimage_path)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        fragment_public_profile_list_small_image!!.layoutManager = layoutManager
        fragment_public_profile_list_small_image!!.adapter = adapter


    }

    fun displaySetting() {
        val fm = fragmentManager
        val dialog = PublicProfileSettingDialog()
        dialog.setTargetFragment(this@PublicProfileFragment, 200)
        dialog.show(fm!!, "request setting")
    }

    fun requested_photo() {
        val fm = fragmentManager
        val dialog = PublicProfileRequestPhotoDialog()
        dialog.setTargetFragment(this@PublicProfileFragment, 300)
        dialog.show(fm!!, "request photo")

    }

    fun likeprofile() {
        val dialog = PublicProfileLikeDialog()
        dialog.setListener(this)

        dialog.show(fragmentManager!!, "dolike profile")

    }


    private fun prepareMock() {
        for (i in PublicProfile_Mocking_Constant.image_path.indices) {
            smallpic_path.add(PublicProfile_Mocking_Constant.image_path[i])
        }

    }


    fun back() {
        var fragment: SearchFragment
        fragment = SearchFragment()

        val ft: FragmentTransaction? = fragmentManager!!.beginTransaction()
        ft!!.replace(R.id.main_fragment, fragment, "SearchFragment")
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }


    override fun onClickedDialogButton(clicked: Boolean) {
        if (!clicked) {
            fragment_public_profile_btn_request_photo!!.setImageResource(R.drawable.photo_requested)
        }
        fragment_public_profile_btn_request_photo!!.isClickable = false

    }


    private fun request_like() {

        var current_object: CLUser? = SearchUserAdapter.Companion.current_user_obj
        var mLikeObj: LikeUserObj? = LikeUserObj()
        mLikeObj!!.target_user_id = current_object!!.id.toString()
        mLikeObj!!.greeting = current_object!!.self_introduction
        mLikeObj.platform_id = 2

        var mClUsers: Call<LikeResponse> = MainActivity.Companion.mRetrofitAPI!!.likeToId(mLikeObj)
        mClUsers.enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                val temp = response.body()
                var st = response.toString()
                Log.d("binh", "log is " + st)


            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {


            }
        })

    }

    private fun request_like(greeting: String) {


        var current_object: CLUser? = SearchUserAdapter.Companion.current_user_obj
        var mLikeObj: LikeUserObj? = LikeUserObj()
        mLikeObj!!.target_user_id = current_object!!.id.toString()
        mLikeObj!!.greeting = greeting
        mLikeObj.platform_id = 2

        var mClUsers: Call<LikeResponse> = MainActivity.Companion.mRetrofitAPI!!.likeToId(mLikeObj)
        mClUsers.enqueue(object : Callback<LikeResponse> {
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                val temp = response.body()
                var st = response.toString()
                Log.d("binh", "log is " + st)


            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {


            }
        })

    }

    override fun ListentoDetailDialog(check: Boolean) {
        if (check) {
            fragment_public_profile_btn_like!!.setBackgroundResource(R.mipmap.btn_like_disabled)
            fragment_public_profile_btn_like!!.isClickable = false


        }

    }

    override fun ListentoGreetingFromDetailDialog(greeting: String) {
        request_like(greeting)
    }


    override fun ListentoLikeDialog(check: Boolean) {
        if (check) {
            fragment_public_profile_btn_like!!.setBackgroundResource(R.mipmap.btn_like_disabled)
            fragment_public_profile_btn_like!!.isClickable = false
            request_like()
        }
    }

    //


    inner class getUserDetailTask() : AsyncTask<Void, Void, Void>() {
        var mProgress: ProgressDialog? = null
        override fun onPreExecute() {
            super.onPreExecute()
            mProgress = ProgressDialog(context)
            mProgress!!.show()

        }

        override fun doInBackground(vararg params: Void?): Void? {

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mProgress!!.dismiss()

        }
    }


    fun getUserDetailObj(callback: UserDetailListener) {


        var mClUsers: Call<DataPublic> = MainActivity.Companion.mRetrofitAPI!!.getProfileDetail(SearchUserAdapter.Companion.current_id.toString())
        mClUsers.enqueue(object : Callback<DataPublic> {
            override fun onResponse(call: Call<DataPublic>, response: Response<DataPublic>) {
                val temp = response.body()
                callback.onSuccess(temp!!.data!!)


            }

            override fun onFailure(call: Call<DataPublic>, t: Throwable) {


            }
        })

    }
}
