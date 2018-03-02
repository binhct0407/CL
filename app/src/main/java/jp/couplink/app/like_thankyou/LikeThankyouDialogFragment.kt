package jp.couplink.app.like_thankyou

import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.mocking.UserObject
import jp.couplink.app.public_profile.model.LikeResponse
import jp.couplink.app.public_profile.model.LikeUserObj
import kotlinx.android.synthetic.main.fragment_like_thankyou.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by BinhTran on 12/27/2017.
 */

class LikeThankyouDialogFragment : DialogFragment() {
    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            d.window.setLayout(width, height)
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_like_thankyou, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLikeUnread(object : LikeThankyouListener {
            override fun onSuccess(mObject: LikeUnreadObj) {

                updateView(mObject)
                var current_pos: Int = like_thankyou_viewpager.currentItem

                fragment_like_thankyou_btn_post_like.setOnClickListener(View.OnClickListener {
                    var mLike: LikeUserObj? = LikeUserObj()
                    mLike!!.target_user_id = mObject.data!![current_pos].target_user!!.id.toString()
                    mLike!!.greeting = ""
                    mLike!!.platform_id = 2
                    postLike(mLike)
                })
            }
        })


    }

    private fun postLike(mLikeObj: LikeUserObj) {
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

    private fun updateView(mLikeObject: LikeUnreadObj) {
        var array: MutableList<UserObject>? = mutableListOf()
        for (i in 0..mLikeObject.data!!.size - 1) {
            array!!.add(mLikeObject!!.data!![i])

        }
        like_thankyou_viewpager!!.adapter = CustomPagerAdapter(activity, array!!)

    }

    fun nextpage() {
        like_thankyou_viewpager!!.setCurrentItem(like_thankyou_viewpager!!.currentItem + 1, true)
    }


    fun getLikeUnread(callback: LikeThankyouListener) {


        var mClUsers: Call<LikeUnreadObj> = MainActivity.Companion.mRetrofitAPI!!.getLikeUnreadResource()
        mClUsers.enqueue(object : Callback<LikeUnreadObj> {
            override fun onResponse(call: Call<LikeUnreadObj>, response: Response<LikeUnreadObj>) {
                val temp = response.body()
                callback.onSuccess(temp!!)


            }

            override fun onFailure(call: Call<LikeUnreadObj>, t: Throwable) {


            }
        })

    }

    internal inner class CustomPagerAdapter(var mContext: Context, var mList: MutableList<UserObject>) : PagerAdapter() {
        var mLayoutInflater: LayoutInflater

        init {
            mLayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getCount(): Int {
            return mList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as LinearLayout
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val itemView = mLayoutInflater.inflate(R.layout.item_like_thank_layout, container, false)
            val imageView = itemView.findViewById<ImageView>(R.id.fragment_like_thankyou_imageFirst)
            val fragment_like_thankyou_target_username = itemView.findViewById<TextView>(R.id.fragment_like_thankyou_target_username)
            val fragment_like_thankyou_target_self_introduction = itemView.findViewById<TextView>(R.id.fragment_like_thankyou_target_self_introduction)
            if (mList.size > 0 && mList.get(position).target_user!!.images!!.size > 0) {
                Glide.with(mContext)
                        .load(mList.get(position).target_user!!.images!![0]!!.medium)
                        .into(imageView)

                fragment_like_thankyou_target_username.text = mList.get(position).target_user!!.name
                fragment_like_thankyou_target_self_introduction.text = mList.get(position).target_user!!.self_introduction
            }
            container.addView(itemView)

            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as LinearLayout)
        }
    }

}
