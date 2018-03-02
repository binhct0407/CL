package jp.couplink.app.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import jp.couplink.app.adapter.FilterSetttingListviewAdapter
import jp.couplink.app.constants.Constants
import jp.couplink.app.utils.PreferencesConstant
import kotlinx.android.synthetic.main.actionbar_filterfriend.*
import kotlinx.android.synthetic.main.activity_filter_friend.*
import jp.couplink.R

/**
 * Created by BinhTran on 11/30/2017.
 */

class FilterFriendActivity : AppCompatActivity() {
    private var mFilterSettingAdapter: FilterSetttingListviewAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_friend)
        mFilterSettingAdapter = FilterSetttingListviewAdapter(this, Constants.fiter_title_name)
        filter_friend_listview!!.adapter = mFilterSettingAdapter


        filter_friend_listview!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (position != 0 && position != 3) {
                val intent = Intent(this, MultiChoiceSettingActivity::class.java)
                intent.putExtra("pos_in_filter_main", position)
                startActivity(intent)
            }
        }
        filter_friend_btn_confirm_filter.setOnClickListener(View.OnClickListener { confirmSearch() })
        filter_friend_actionbar_back.setOnClickListener(View.OnClickListener { onBackPressed() })
        filter_friend_actionbar_reset_filter.setOnClickListener(View.OnClickListener { resetData() })


    }


    fun confirmSearch() {
        val intent = Intent()
        if (filter_yearold_start > 0 && filter_yearold_end > 0) {
            val sharedPref_name: String = PreferencesConstant.getPreferenceName(0)
            val sharedPref: SharedPreferences = getSharedPreferences(sharedPref_name, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()
            editor.putBoolean("filtered", true)
            editor.putInt("yearold_start", filter_yearold_start)
            editor.putInt("yearold_end", filter_yearold_end)
            editor.commit()

        }
        if (filter_height_start > 0 && filter_height_end > 0) {
            val pre = getSharedPreferences(PreferencesConstant.getPreferenceName(3), Context.MODE_PRIVATE)
            val editor = pre.edit()
            editor.putBoolean("filtered", true)
            editor.putInt("filter_height_start", filter_height_start)
            editor.putInt("filter_height_end", filter_height_end)
            editor.commit()

        }
        onBackPressed()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun resetData() {
        for (i in 0..9) {
            val pre = getSharedPreferences(PreferencesConstant.getPreferenceName(i), Context.MODE_PRIVATE)
            pre.edit().clear().commit()
        }
        filter_yearold_start = 0
        filter_yearold_end = 0
        filter_height_start = 0
        filter_height_end = 0
        mFilterSettingAdapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        if (filter_yearold_start > 0 && filter_yearold_end > 0) {
            val pre = getSharedPreferences(PreferencesConstant.getPreferenceName(0), Context.MODE_PRIVATE)
            val editor = pre.edit()
            editor.putBoolean("filtered", true)
            editor.putInt("yearold_start", filter_yearold_start)
            editor.putInt("yearold_end", filter_yearold_end)
            editor.commit()

        }
        if (filter_height_start > 0 && filter_height_end > 0) {
            val pre = getSharedPreferences(PreferencesConstant.getPreferenceName(3), Context.MODE_PRIVATE)
            val editor = pre.edit()
            editor.putBoolean("filtered", true)
            editor.putInt("filter_height_start", filter_height_start)
            editor.putInt("filter_height_end", filter_height_end)
            editor.commit()

        }
    }

    companion object {
        var filter_yearold_start = 0
        var filter_yearold_end = 0
        var filter_height_start = 0
        var filter_height_end = 0
        var filter_year_start_text = ""
        var filter_year_end_text = ""
        var filter_height_start_text = ""
        var filter_height_end_text = ""
    }
}
