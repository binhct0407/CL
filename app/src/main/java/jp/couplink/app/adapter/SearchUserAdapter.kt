package jp.couplink.app.adapter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.database.entities.CLUser
import jp.couplink.app.public_profile.PublicProfileFragment
import jp.couplink.app.public_profile.model.LikeResponse
import jp.couplink.app.public_profile.model.LikeUserObj
import kotlinx.android.synthetic.main.item_search_user_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * Created by BinhTran on 11/28/2017.
 */

class SearchUserAdapter(private val context: Context, private val mUserList: MutableList<CLUser>?) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {

    companion object {
        var current_user_obj: CLUser? = null
        var user_url: String = ""
        var current_id: Int = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_user_layout, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = mUserList!![position]

        try {
            holder.bindView(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    //Add data to adapter
    fun add(user: CLUser) {
        mUserList!!.add(user)
        notifyDataSetChanged()
    }

    //Clear data from adapter
    fun clear() {
        mUserList!!.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mUserList?.size ?: 0
    }

    class ViewHolder
    (view: View) : RecyclerView.ViewHolder(view) {


        fun bindView(user: CLUser) = with(itemView) {
            if (user.is_liked_by_me) {
                item_search_user_img_heart_like!!.setBackgroundResource(R.drawable.selector)
                item_search_user_img_heart_like.isClickable = false
            }

            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            item_search_user_imgContainer.layoutParams.height = display.width / 2 - 5
            item_search_user_txtStatus!!.text = user.body_shape_type
            item_search_user_txtShareCount!!.text = user.common_point.toString()
            item_search_user_txtUserInfo!!.text = user.age.toString() + " " + user.residence_prefecture_name

            item_search_user_txtMessage!!.visibility = if (TextUtils.isEmpty(user.body_shape_type)) View.GONE else View.VISIBLE
            item_search_user_txtMessage!!.text = user.self_introduction

            item_search_user_txtNotes!!.visibility = if (TextUtils.isEmpty(user.job)) View.GONE else View.VISIBLE
            item_search_user_txtNotes!!.text = user.job

            Glide.with(item_search_user_imgMain!!.context)
                    .load(user!!.top_image!!.original)
                    .into(item_search_user_imgMain!!)
            item_search_user_img_heart_like!!.setOnClickListener {
                //implement change pic here
                item_search_user_img_heart_like!!.setBackgroundResource(R.drawable.selector)
                request_like_user(user)
            }
            //load sub button_image
            val sub_images_path = ArrayList<String>()
            if (user.sub_images != null && user.sub_images!!.size > 0) {
                for (i in 0 until user.sub_images!!.size) {
                    sub_images_path.add(user!!.sub_images!![i]!!.small.toString())
                }
            }

            user_url = user.url.toString()

            item_search_user_imgMain!!.setOnClickListener { v ->
                val activity = v.context as AppCompatActivity
                val fragment: PublicProfileFragment
                fragment = PublicProfileFragment()
                current_id = user.id
                current_user_obj = user

                activity.supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit()
            }


            val length = sub_images_path.size
            if (length > 1) {

                val adapter = SearchUserSubImageAdapter(sub_images_path)
                val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                item_search_user_userImageListView!!.layoutManager = layoutManager
                item_search_user_userImageListView!!.adapter = adapter
                item_search_user_userImageListView!!.visibility = View.VISIBLE
            } else {
                item_search_user_userImageListView!!.visibility = View.GONE
            }


        }

        private fun request_like_user(mClUser: CLUser) {


            var mLikeObj: LikeUserObj? = LikeUserObj()
            mLikeObj!!.target_user_id = mClUser!!.id.toString()
            mLikeObj!!.greeting = mClUser!!.self_introduction
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


    }
}
