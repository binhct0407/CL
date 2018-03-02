package jp.couplink.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.item_group_basic_infor.view.*

/**
 * Created by BinhTran on 11/28/2017.
 */

class GroupBasicInforAdapter(internal var filter_title: MutableList<String>, internal var detail: MutableList<String>) : RecyclerView.Adapter<GroupBasicInforAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group_basic_infor, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(filter_title!!.get(position), detail!!.get(position))
    }

    override fun getItemCount(): Int {
        return if (filter_title == null) 0 else filter_title!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(title_name: String, detail_text: String) = with(itemView) {

            filter_title_name!!.text = title_name
            basic_infor_detail!!.text = detail_text
        }
    }
}
