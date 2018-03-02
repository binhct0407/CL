package jp.couplink.app.public_profile

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.dialog_public_profile_like.*

/**
 * Created by BinhTran on 12/21/2017.
 */

class PublicProfileLikeDialog : DialogFragment() {
    internal lateinit var listener: DialogFragmentListener

    fun setListener(listener: DialogFragmentListener) {
        this.listener = listener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog_publicprofile_like_sendmessage.setOnClickListener(View.OnClickListener { dolikewithmessage() })
        dialog_publicprofile_like_dialog_like.setOnClickListener(View.OnClickListener { dolike() })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_public_profile_like, container, false)
    }

    fun dolikewithmessage() {
        val dialog = PublicProfileLikeDetailDialog()
        dialog.setListener(listener)
        dialog.show(fragmentManager!!, "dolike detail")
        dismiss()

    }

    fun dolike() {
        listener.ListentoLikeDialog(true)
        dismiss()
    }


}
