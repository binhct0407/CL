package jp.couplink.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.couplink.R
import jp.couplink.app.utils.GlideImageConstant
import jp.couplink.app.utils.ResourceUtils
import jp.couplink.app.utils.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_publicprofile_common_layout.view.*

/**
 * Created by BinhTran on 11/28/2017.
 */

class PublicProfileCommonAdapter(internal var imgIds: MutableList<String>?) : RecyclerView.Adapter<PublicProfileCommonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_publicprofile_common_layout, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(imgIds!!.get(position))
    }

    override fun getItemCount(): Int {
        return if (imgIds == null) 0 else imgIds!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(id: String) = with(itemView) {
            var real_pth: String = "R.drawable." + id
            Glide.with(itemView.context)
                    .load(ResourceUtils.Companion.getImage(itemView.context,id))
                    .apply(RequestOptions.bitmapTransform(
                            RoundedCornersTransformation(itemView.context, GlideImageConstant.sCorner, GlideImageConstant.sMargin)))
                    .into(image!!)


        }
    }


}
