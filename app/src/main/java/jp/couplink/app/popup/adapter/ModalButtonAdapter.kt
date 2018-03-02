package jp.couplink.app.popup.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import jp.couplink.R
import jp.couplink.app.popup.FragmentPopup
import jp.couplink.app.utils.ResourceUtils
import kotlinx.android.synthetic.main.item_popup_typemodal_button.view.*

/**
 * Created by BinhTran on 11/28/2017.
 */

class ModalButtonAdapter(internal var list_url: MutableList<String>) : RecyclerView.Adapter<ModalButtonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_popup_typemodal_button, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(list_url!!.get(position))
    }

    override fun getItemCount(): Int {
        return if (list_url == null) 0 else list_url!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(title_name: String) = with(itemView) {
            /*
                Glide.with(context)
                        .load(ResourceUtils.getImage(context, title_name))
                        .into(item_popup_typemodal_button_image!!)
    */

            item_popup_typemodal_button_image.setImageResource(ResourceUtils.getImage(context, title_name))
            item_popup_typemodal_button_image.setOnClickListener(View.OnClickListener { })


        }
    }

}
