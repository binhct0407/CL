package jp.couplink.app.purchase.membership

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import jp.couplink.R
import kotlinx.android.synthetic.main.item_purchase_images.view.*

/**
 * Created by BinhTran on 12/11/2017.
 */
class PurchaseImageAdapter(internal var purchaseImages: MutableList<String>?) : RecyclerView.Adapter<PurchaseImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_images, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(purchaseImages!!.get(position))
    }

    override fun getItemCount(): Int {
        return purchaseImages!!.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindView(mPath: String) = with(itemView) {
            Glide.with(itemView.context)
                    .load(mPath)
                    .into(item_puarchase_image)

        }
    }
}
