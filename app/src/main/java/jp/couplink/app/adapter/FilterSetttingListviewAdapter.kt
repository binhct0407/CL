package jp.couplink.app.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

import java.util.ArrayList

import jp.couplink.R
import jp.couplink.app.activity.FilterFriendActivity
import jp.couplink.app.utils.PreferencesConstant


class FilterSetttingListviewAdapter(private val context: Activity,
                                    private val filter_title_name: Array<String>) : ArrayAdapter<String>(context, R.layout.item_filter_setting, filter_title_name) {


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.getLayoutInflater()
        val rowView = inflater.inflate(R.layout.item_filter_setting, null, true)
        val filter_content_1 = rowView.findViewById<TextView>(R.id.filter_content_1)
        val filter_content_2 = rowView.findViewById<TextView>(R.id.filter_content_2)
        val filter_content_3 = rowView.findViewById<TextView>(R.id.filter_content_3)

        if ((position != 0) and (position != 3)) {
            filter_content_1.setVisibility(View.GONE)
            filter_content_2.setVisibility(View.GONE)


            val pre = context.getSharedPreferences(PreferencesConstant.getPreferenceName(position), Context.MODE_PRIVATE)
            val check_multi = pre.getBoolean("filtered", false)
            val condition = pre.getString("title_multicheck", "")
            val set = pre.getStringSet("key", null)
            if (check_multi) {
                filter_content_3.setText(condition)
                filter_content_3.setTextColor(Color.parseColor("#7acee0"))
            }

        } else {
            if (position == 0) {

                val pre = context.getSharedPreferences(PreferencesConstant.getPreferenceName(position), Context.MODE_PRIVATE)
                val check_filtered = pre.getBoolean("filtered", false)
                val start = pre.getInt("yearold_start", 0)
                val end = pre.getInt("yearold_end", 0)
                if (check_filtered) {
                    filter_content_1.setText(Integer.toString(start) + " 歳")
                    filter_content_3.setText(Integer.toString(end) + " 歳")
                    filter_content_1.setTextColor(Color.parseColor("#7acee0"))
                    filter_content_3.setTextColor(Color.parseColor("#7acee0"))

                }

                filter_content_1.setOnClickListener(object : View.OnClickListener {
                    public override fun onClick(v: View) {
                        val yearold_title = ArrayList<String>()
                        yearold_title.add("こだわらない")
                        for (i in 20..65) {
                            yearold_title.add(i.toString() + " 歳")
                        }
                        val adapter = FilterYearOldDiaglogAdapter(context, 0, yearold_title)
                        val dialog = Dialog(context)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.filter_yearold_layout)
                        val listview = dialog.findViewById<View>(R.id.listExample) as ListView


                        listview.setAdapter(adapter)


                        dialog.show()
                        listview.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                            public override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                filter_content_1.setText(yearold_title.get(position))
                                if (position > 0) {
                                    filter_content_1.setTextColor(Color.parseColor("#7acee0"))


                                    FilterFriendActivity.filter_yearold_start = position + 19
                                    FilterFriendActivity.filter_year_start_text = filter_content_1.getText().toString()
                                }
                                dialog.dismiss()
                            }
                        })

                    }
                })
                filter_content_3.setOnClickListener(object : View.OnClickListener {
                    public override fun onClick(v: View) {
                        val yearold_title = ArrayList<String>()
                        yearold_title.add("こだわらない")
                        for (i in 20..65) {
                            yearold_title.add(i.toString() + " 歳")
                        }
                        val adapter = FilterYearOldDiaglogAdapter(context, 0, yearold_title)
                        val dialog = Dialog(context)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.filter_yearold_layout)
                        val listview = dialog.findViewById<View>(R.id.listExample) as ListView

                        listview.setAdapter(adapter)


                        dialog.show()
                        listview.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                            public override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                filter_content_3.setText(yearold_title.get(position))
                                if (position > 0) {
                                    filter_content_3.setTextColor(Color.parseColor("#7acee0"))


                                    FilterFriendActivity.filter_yearold_end = position + 19
                                    FilterFriendActivity.filter_year_end_text = filter_content_3.getText().toString()
                                }
                                dialog.dismiss()

                            }
                        })
                    }
                })
                if (FilterFriendActivity.filter_yearold_start > 20 && FilterFriendActivity.filter_yearold_end > 20) {
                    val pre_year = context.getSharedPreferences(PreferencesConstant.getPreferenceName(0), Context.MODE_PRIVATE)
                    val editor = pre_year.edit()
                    editor.putBoolean("filtered", true)

                    editor.commit()

                }

            }
            if (position == 3) {
                val pre = context.getSharedPreferences(PreferencesConstant.getPreferenceName(position), Context.MODE_PRIVATE)
                val check_filtered = pre.getBoolean("filtered", false)
                val start = pre.getInt("filter_height_start", 0)
                val end = pre.getInt("filter_height_end", 0)
                if (check_filtered) {
                    filter_content_1.setText(Integer.toString(start) + " cm")
                    filter_content_3.setText(Integer.toString(end) + " cm")
                    filter_content_1.setTextColor(Color.parseColor("#7acee0"))
                    filter_content_3.setTextColor(Color.parseColor("#7acee0"))

                }
                filter_content_1.setOnClickListener(object : View.OnClickListener {
                    public override fun onClick(v: View) {
                        val height_title = ArrayList<String>()
                        height_title.add("こだわらない")
                        for (i in 130..200) {
                            height_title.add(i.toString() + " cm")
                        }
                        val adapter = FilterHeightDiaglogAdapter(context, 0, height_title)
                        val dialog = Dialog(context)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.filter_yearold_layout)
                        val listview = dialog.findViewById<View>(R.id.listExample) as ListView


                        listview.setAdapter(adapter)


                        dialog.show()
                        listview.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                            public override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                filter_content_1.setText(height_title.get(position))
                                if (position > 0) {
                                    filter_content_1.setTextColor(Color.parseColor("#7acee0"))
                                }
                                FilterFriendActivity.filter_height_start = position + 129
                                FilterFriendActivity.filter_height_start_text = filter_content_1.getText().toString()
                                dialog.dismiss()
                            }
                        })

                    }
                })
                filter_content_3.setOnClickListener(object : View.OnClickListener {
                    public override fun onClick(v: View) {
                        val height_title = ArrayList<String>()
                        height_title.add("こだわらない")
                        for (i in 130..200) {
                            height_title.add(i.toString() + " cm")
                        }
                        val adapter = FilterHeightDiaglogAdapter(context, 0, height_title)
                        val dialog = Dialog(context)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.filter_yearold_layout)
                        val listview = dialog.findViewById<View>(R.id.listExample) as ListView

                        listview.setAdapter(adapter)


                        dialog.show()
                        listview.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                            public override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                filter_content_3.setText(height_title.get(position))
                                if (position > 0) {
                                    filter_content_3.setTextColor(Color.parseColor("#7acee0"))
                                }

                                FilterFriendActivity.filter_height_end = position + 129
                                FilterFriendActivity.filter_height_end_text = filter_content_3.getText().toString()
                                dialog.dismiss()

                            }
                        })
                    }
                })

            }
        }


        val txtTitle = rowView.findViewById<TextView>(R.id.filter_title_name)
        txtTitle.setText(filter_title_name[position])
        return rowView
    }
}
