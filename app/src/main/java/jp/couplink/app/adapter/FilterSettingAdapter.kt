package jp.couplink.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.item_filter_setting.view.*

/**
 * Created by BinhTran on 11/28/2017.
 */

class FilterSettingAdapter(internal var filter_title: Array<String>?) : RecyclerView.Adapter<FilterSettingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_setting, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(filter_title!![position])
    }

    override fun getItemCount(): Int {
        return if (filter_title == null) 0 else filter_title!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindView(title_name: String) = with(itemView) {

            filter_title_name!!.text = title_name
        }
    }
}
