package jp.couplink.app.public_profile

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.custom_dialog.*

/**
 * Created by BinhTran on 12/21/2017.
 */

class PublicProfileRequestPhotoDialog : DialogFragment() {
    interface DialogButtonListener {
        fun onClickedDialogButton(clicked: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.custom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params = dialog.window!!.attributes // change this to your dialog.

        params.y = -50 // Here is the param to set your dialog position. Same with params.x
        dialog.window!!.attributes = params
        dialog.window!!.setGravity(Gravity.CENTER_HORIZONTAL)
        custom_dialog_btn_ok!!.setOnClickListener(View.OnClickListener { setData() })
        custom_dialog_btn_cancel!!.setOnClickListener(View.OnClickListener { cancel() })

    }

    fun setData() {
        custom_dialog_btn_ok!!.isClickable = false
        val listener = targetFragment as DialogButtonListener?
        listener!!.onClickedDialogButton(custom_dialog_btn_ok!!.isClickable)
        dismiss()
    }

    fun cancel() {
        dismiss()
    }


}
