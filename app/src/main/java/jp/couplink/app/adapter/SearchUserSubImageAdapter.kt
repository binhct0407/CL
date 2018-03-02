package jp.couplink.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList
import jp.couplink.R
import jp.couplink.app.utils.GlideImageConstant
import jp.couplink.app.utils.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_user_sub_image_layout.view.*

/**
 * Created by BinhTran on 11/28/2017.
 */

class SearchUserSubImageAdapter(internal var imgIds: ArrayList<String>?) : RecyclerView.Adapter<SearchUserSubImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_sub_image_layout, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(imgIds!![position])
    }

    override fun getItemCount(): Int {
        return if (imgIds == null) 0 else imgIds!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(id: String) = with(itemView) {
            Glide.with(itemView.context)
                    .load(  id)
                    .apply(RequestOptions.bitmapTransform(
                            RoundedCornersTransformation(itemView.context, GlideImageConstant.sCorner, GlideImageConstant.sMargin)))
                    .into(item_user_sub_image_image!!)


        }
    }
}
