package jp.couplink.app.message

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import java.util.ArrayList
import jp.couplink.R
import jp.couplink.app.database.entities.CLUser
import jp.couplink.app.mocking.TargetUser
import jp.couplink.app.mocking.UserObject
import kotlinx.android.synthetic.main.item_message_matched_user.view.*

/**
 * Created by BinhTran on 12/11/2017.
 */
class RoomMessagingAdapter(internal var matchedUsers: MutableList<TargetUser>) : RecyclerView.Adapter<RoomMessagingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomMessagingAdapter.ViewHolder {
        return RoomMessagingAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_matched_user, null))
    }

    override fun onBindViewHolder(holder: RoomMessagingAdapter.ViewHolder, position: Int) {
        holder.bindView(matchedUsers.get(position))
    }

    override fun getItemCount(): Int {
        return matchedUsers.size
    }

    //Add data to adapter
    fun add(user: TargetUser) {
        matchedUsers!!.add(user)
        notifyDataSetChanged()
    }

    //Clear data from adapter
    fun clear() {
        matchedUsers!!.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindView(user: TargetUser) = with(itemView) {
            Glide.with(itemView.context)
                    .load(user.images!![0].small)
                    .into(item_message_matched_icon_message_matched_user!!)
            item_message_matched_name_matched_message!!.text = user.name
            item_message_matched_detail_matched_message!!.text = user.self_introduction

            if (adapterPosition % 3 == 0) {
                txt_unread!!.visibility = View.VISIBLE
                item_message_matched_txt_conor!!.visibility = View.VISIBLE
            } else {
                txt_unread!!.visibility = View.GONE
                item_message_matched_txt_conor!!.visibility = View.INVISIBLE
            }
        }
    }
}
