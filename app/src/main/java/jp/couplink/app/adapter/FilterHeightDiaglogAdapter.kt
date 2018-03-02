package jp.couplink.app.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import jp.couplink.R

/**
 * Created by BinhTran on 12/1/2017.
 */

class FilterHeightDiaglogAdapter(context: Context, textViewResourceId: Int,
                                 objects: List<String>) : ArrayAdapter<String>(context, textViewResourceId, objects) {
    var selectedPosition = -1
        set(pos) {
            field = pos
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val vi = this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(R.layout.item_filter_yearold_dialog, null)
        }
        val label = v!!.findViewById<View>(R.id.txtExample) as TextView
        if (selectedPosition == position) {
            label.setBackgroundColor(Color.GREEN)
        } else {
            label.setBackgroundColor(Color.WHITE)
        }
        label.text = this.getItem(position)!!.toString()
        return v
    }
}