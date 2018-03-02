package jp.couplink.app.message

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import java.util.ArrayList
import jp.couplink.R
import kotlinx.android.synthetic.main.item_message_list.view.*

/**
 * Created by BinhTran on 12/11/2017.
 */
class MessageListAdapter(internal var imgIds: ArrayList<String>, internal var names: ArrayList<String>, internal var details: ArrayList<String>) : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListAdapter.ViewHolder {
        return MessageListAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_list, null))
    }

    override fun onBindViewHolder(holder: MessageListAdapter.ViewHolder, position: Int) {
        holder.bindView(imgIds[position], names[position], details[position])
    }

    override fun getItemCount(): Int {
        return names.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(id: String, name: String, deatail: String) = with(itemView) {
            Glide.with(itemView.context)
                    .load("http://13.113.80.129:3000/" + id)
                    .into(item_message_list_icon_message_matched_user!!)
            item_message_list_name_matched_message!!.text = name
            val pre = itemView.context.getSharedPreferences("message_preference", Context.MODE_PRIVATE)
            val last_message = pre.getString(name, "")
            val check_read = pre.getBoolean("check_" + name, false)
            item_message_list_txt_last_message!!.text = last_message
            if (check_read) {
                item_message_list_txt_last_message!!.setTextColor(Color.BLACK)
                item_message_list_txt_last_message!!.setTypeface(null, Typeface.BOLD)
            } else {
                item_message_list_txt_last_message!!.setTextColor(Color.parseColor("#616161"))
                item_message_list_txt_last_message!!.setTypeface(null, Typeface.NORMAL)
            }

            if (adapterPosition % 3 == 0)
                item_message_list_txt_conor!!.visibility = View.VISIBLE
            else
                item_message_list_txt_conor!!.visibility = View.INVISIBLE
        }
    }
}
