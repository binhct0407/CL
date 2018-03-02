package jp.couplink.app.public_profile

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.public_profile_like_detail_dialog.*
import android.text.Editable

/**
 * Created by BinhTran on 12/21/2017.
 */

class PublicProfileLikeDetailDialog : DialogFragment() {

    internal var listener: DialogFragmentListener? = null

    fun setListener(listener: DialogFragmentListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialogdetail)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.public_profile_like_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window!!.setGravity(Gravity.CENTER)
        like_detail_dialog_txt_send.setOnClickListener(View.OnClickListener { send_like_with_message() })
        edt_detail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                txt_count_string.setText(s.toString().length.toString() + "/200")

            }
        })
    }


    fun send_like_with_message() {
        listener!!.ListentoDetailDialog(true)
        listener!!.ListentoGreetingFromDetailDialog(edt_detail.text.toString())
        dismiss()
    }


}
