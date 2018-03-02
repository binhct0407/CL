package jp.couplink.app.purchase.membership

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import jp.couplink.R
import jp.couplink.app.purchase.model.CoinPurchaseItem
import kotlinx.android.synthetic.main.item_purchase_button.view.*

/**
 * Created by BinhTran on 12/11/2017.
 */
class PurchaseCoinButtonAdapter(internal var purchaseBtn: MutableList<CoinPurchaseItem>?) : RecyclerView.Adapter<PurchaseCoinButtonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_button, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(purchaseBtn!!.get(position).button_image!!)
    }

    override fun getItemCount(): Int {
        return purchaseBtn!!.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindView(mPath: String) = with(itemView) {
            Glide.with(itemView.context)
                    .load(mPath)
                    .into(item_purchase_btn)

        }
    }
}
