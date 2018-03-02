package jp.couplink.app.fragment.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * 共通プログレスダイアログ
 * Created by u-suke on 2017/02/22.
 */

class ProgressDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val message = bundle!!.getString(BUNDLE_MESSAGE)
        val title = bundle.getString(BUNDLE_TITLE)
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage(message)
        if (!title!!.isEmpty()) {
            progressDialog.setTitle(title)
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        return progressDialog
    }

    companion object {
        private val BUNDLE_TITLE = "title"
        private val BUNDLE_MESSAGE = "msg"

        fun getInstance(message: String): ProgressDialogFragment {
            return getInstance("", message)
        }

        fun getInstance(title: String, message: String): ProgressDialogFragment {
            val bundle = Bundle()
            bundle.putString(BUNDLE_TITLE, title)
            bundle.putString(BUNDLE_MESSAGE, message)

            val dialog = ProgressDialogFragment()

            dialog.arguments = bundle

            return dialog
        }
    }
}
