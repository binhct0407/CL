package jp.couplink.app.popup

import android.app.DialogFragment
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide

import jp.couplink.R;
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.popup.listener.MatchedUnreadListener
import jp.couplink.app.popup.model.MatchedObj
import kotlinx.android.synthetic.main.fragment_matched_unread.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by BinhTran on 12/27/2017.
 */

class FragmentMatchedUnread : DialogFragment() {

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
        return inflater!!.inflate(R.layout.fragment_matched_unread, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMatchedUnread(object : MatchedUnreadListener {
            override fun onSuccess(mObject: MatchedObj) {

                Log.d("binh", mObject.toString())
                updateView(mObject)
            }
        })


    }

    private fun updateView(mObject: MatchedObj) {
        var mList: MutableList<String>? = mutableListOf()
        for (i in 0..mObject.data!!.images.size - 1) {
            mList!!.add(mObject.data!!.images[i]!!.image_path!!)

        }
        fragment_matched_unread_layoutMain.setBackgroundColor(Color.parseColor(mObject.data!!.bg_color))
        fragment_matched_unread_nextpage.setTextColor(Color.parseColor(mObject.data!!.bg_color))
        fragment_matched_viewpager!!.adapter = CustomPagerAdapter(activity, mList!!)

    }

    fun getMatchedUnread(callback: MatchedUnreadListener) {


        var mClUsers: Call<MatchedObj> = MainActivity.Companion.mRetrofitAPI!!.getMatchedUnread()
        mClUsers.enqueue(object : Callback<MatchedObj> {
            override fun onResponse(call: Call<MatchedObj>, response: Response<MatchedObj>) {
                val temp = response.body()
                callback.onSuccess(temp!!)


            }

            override fun onFailure(call: Call<MatchedObj>, t: Throwable) {


            }
        })

    }


    internal inner class CustomPagerAdapter(var mContext: Context, var mListMatched: MutableList<String>) : PagerAdapter() {
        var mLayoutInflater: LayoutInflater

        init {
            mLayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getCount(): Int {
            return mListMatched.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as RelativeLayout
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val itemView = mLayoutInflater.inflate(R.layout.item_matched_unread, container, false)
            val imageView = itemView.findViewById<ImageView>(R.id.fragment_matched_unread_images)
            Glide.with(mContext)
                    .load(mListMatched.get(position))
                    .into(imageView)

            container.addView(itemView)

            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as RelativeLayout)
        }
    }

}
