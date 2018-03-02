package jp.couplink.app.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import jp.couplink.R

/**
 * Created by BinhTran on 1/4/2018.
 */

class SettingGridAdapter(private val mContext: Context, private val gridViewString: Array<String>, private val gridViewImageId: IntArray) : BaseAdapter() {

    override fun getCount(): Int {
        return gridViewString.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        var gridViewAndroid: View
        val inflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) {

            gridViewAndroid = View(mContext)
            gridViewAndroid = inflater.inflate(R.layout.item_gridview_setting_layout, null)
            val textViewAndroid = gridViewAndroid.findViewById<View>(R.id.txt_gridview_setting) as TextView
            val imageViewAndroid = gridViewAndroid.findViewById<View>(R.id.img_gridview_setting) as ImageView
            textViewAndroid.text = gridViewString[i]
            imageViewAndroid.setImageResource(gridViewImageId[i])
        } else {
            gridViewAndroid = convertView
        }

        return gridViewAndroid
    }

}

