package jp.couplink.app.adapter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import jp.couplink.R
import jp.couplink.app.database.entities.CLUser
import jp.couplink.app.public_profile.PublicProfileFragment
import kotlinx.android.synthetic.main.item_newcomer_list_layout.view.*

/**
 * Created by BinhTran on 11/28/2017.
 */

class NewcomerListAdapter(private val context: Context, private val mUserList: MutableList<CLUser>?) : RecyclerView.Adapter<NewcomerListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_newcomer_list_layout, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = mUserList!![position]

        if (user != null) {
            holder.bindView(user)
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindView(user: CLUser) = with(itemView) {

            txtShareCount!!.text = "1"


            Glide.with(imgMain!!.context)
                    .load(user.top_image!!.original)
                    .into(imgMain!!)

            imgMain!!.setOnClickListener { v ->
                val activity = v.context as AppCompatActivity
                val fragment: PublicProfileFragment
                fragment = PublicProfileFragment()
                activity.supportFragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit()
            }

            btn_new_come_list_free_like!!.setOnClickListener(View.OnClickListener { btn_new_come_list_free_like!!.setBackgroundResource(R.mipmap.btn_free_like_disabled) })


        }


    }
}
