package jp.couplink.app.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.SparseBooleanArray
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import jp.couplink.R
import jp.couplink.app.utils.GetTitleSettingFromJson
import jp.couplink.app.utils.PreferencesConstant
import jp.couplink.app.utils.TitleJSONConstant
import kotlinx.android.synthetic.main.activity_multichoice_setting.*
import kotlinx.android.synthetic.main.filter_multichoice_action_bar_layout.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by BinhTran on 12/6/2017.
 */

class MultiChoiceSettingActivity : AppCompatActivity() {


    internal lateinit var sparseBooleanArray: SparseBooleanArray
    internal var multichoice_title = ArrayList<String>()
    var multichoice_ids: ArrayList<String> = ArrayList()
    private var title_multi_choise: String? = ""
    var pre: SharedPreferences? = null
    var adapter: ArrayAdapter<Any>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_multichoice_setting)

        prepare_Title()


        multichoice_setting_actionbar_reset_filter!!.setOnClickListener { clear_filter() }

        adapter = ArrayAdapter(this@MultiChoiceSettingActivity,
                android.R.layout.simple_list_item_multiple_choice,
                android.R.id.text1, multichoice_title as List<Any>?)


        val i = intent
        val pos = i.getIntExtra("pos_in_filter_main", 0)
        pre = getSharedPreferences(PreferencesConstant.getPreferenceName(pos), Context.MODE_PRIVATE)
        var set_pos = pre!!.getStringSet("key", null)


        multichoice_setting_listview!!.adapter = adapter
        if (set_pos != null) {
            var list_saved = set_pos.toMutableList()
            for (i in list_saved) {
                multichoice_setting_listview!!.setItemChecked(i.toInt() - 1, true)
            }
        }


        multichoice_setting_listview!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            sparseBooleanArray = multichoice_setting_listview!!.checkedItemPositions


            var ValueHolder = ""
            multichoice_ids = ArrayList()
            for (i in 0 until sparseBooleanArray.size()) {
                if (sparseBooleanArray.valueAt(i)) {
                    ValueHolder += multichoice_title[sparseBooleanArray.keyAt(i)] + ","
                    multichoice_ids.add(Integer.toString(sparseBooleanArray.keyAt(i) + 1))
                }
            }

            ValueHolder = ValueHolder.replace("(,)*$".toRegex(), "")

            title_multi_choise = ValueHolder
        }

        multichoice_setting_btn_confirm!!.setOnClickListener {
            val i = intent
            val pos = i.getIntExtra("pos_in_filter_main", 0)
            val pre = getSharedPreferences(PreferencesConstant.getPreferenceName(pos), Context.MODE_PRIVATE)
            val editor = pre.edit()


            val set = HashSet<String>()
            set.addAll(multichoice_ids)
            editor.putStringSet("key", set)
            editor.putString("title_multicheck", title_multi_choise)
            editor.putBoolean("filtered", !title_multi_choise!!.isEmpty())
            editor.putInt("pos", pos)
            editor.commit()
            val intent = Intent()

            intent.setClass(this@MultiChoiceSettingActivity, FilterFriendActivity::class.java)
            startActivity(intent)
        }
        multichoice_setting_actionbar_back.setOnClickListener(View.OnClickListener { onBackPressed() })


    }


    private fun prepare_Title() {
        val intent = intent
        val position_in_FilterActivity = intent.getIntExtra("pos_in_filter_main", 0)
        if (position_in_FilterActivity == 1)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_PREFECTURE, "prefectures")
        if (position_in_FilterActivity == 2)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_JOBS, "jobs")
        if (position_in_FilterActivity == 4)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_BODY_SHAPE, "body_shape_types")
        if (position_in_FilterActivity == 5)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_HOLIDAY_TYPE, "holiday_types")
        if (position_in_FilterActivity == 6)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_SMOKING_TYPE, "smoking_types")
        if (position_in_FilterActivity == 7)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_DRINKING_TYPE, "drinking_types")
        if (position_in_FilterActivity == 8)
            multichoice_title = ArrayList(Arrays.asList("こだわらない",
                    "あり"))
        if (position_in_FilterActivity == 9)
            multichoice_title = GetTitleSettingFromJson.getTitle(TitleJSONConstant.TITLE_LAST_LOGIN_TYPE, "last_login_types")

    }

    fun clear_filter() {
        pre!!.edit().clear().commit()
        multichoice_setting_listview.clearChoices()
        adapter!!.notifyDataSetChanged()


    }


}
