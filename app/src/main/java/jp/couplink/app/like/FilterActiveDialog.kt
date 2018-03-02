package jp.couplink.app.like

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.fragment_favorite_filter_active_dialog.*

/**
 * Created by BinhTran on 12/21/2017.
 */

class FilterActiveDialog : DialogFragment() {
    var filter_active: Boolean = false
    internal lateinit var mFilter: DialogButtonListener
    fun setListener(mFilter: DialogButtonListener) {
        this.mFilter = mFilter
    }

    interface DialogButtonListener {
        fun onClickedDialogButton(clicked: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favorite_filter_active_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var pre = context!!.getSharedPreferences("filter_active", Context.MODE_PRIVATE)
        filter_active = pre.getBoolean("is_filteractive", false);
        if (filter_active) filter_active_dialog_doFilter.text = resources.getString(R.string.fragment_favorite_fiteractive)
        else
            filter_active_dialog_doFilter!!.text = resources.getString(R.string.fragment_favorite_hide_filter)
        val params = dialog.window!!.attributes // change this to your dialog.

        params.y = -50 // Here is the param to set your dialog position. Same with params.x
        dialog.window!!.attributes = params
        dialog.window!!.setGravity(Gravity.CENTER_HORIZONTAL)
        filter_active_dialog_doFilter!!.setOnClickListener(View.OnClickListener { setData() })
        filter_active_dialog_cancel!!.setOnClickListener(View.OnClickListener { cancel() })

    }

    fun setData() {
        filter_active = !filter_active
        var pre = context!!.getSharedPreferences("filter_active", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = pre.edit()
        editor.putBoolean("is_filteractive", filter_active)
        editor.commit()
        if (filter_active) {
            filter_active_dialog_doFilter.text = resources.getString(R.string.fragment_favorite_fiteractive)
        } else
            filter_active_dialog_doFilter!!.text = resources.getString(R.string.fragment_favorite_hide_filter)
        val listener = targetFragment as DialogButtonListener?
        listener!!.onClickedDialogButton(filter_active_dialog_doFilter!!.isClickable)
        dismiss()
    }

    fun cancel() {
        dismiss()
    }


}
