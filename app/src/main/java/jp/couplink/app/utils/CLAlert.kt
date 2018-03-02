package jp.couplink.app.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log

/**
 * Created by BinhTran on 11/23/2017.
 */

class CLAlert {
    companion object {
        val TAG = "CLAlert"

        fun notify(ctx: Context, message: String) {
            val builder = AlertDialog.Builder(ctx)
            builder.setMessage(message)
            builder.setNeutralButton("OK", null)
            builder.create().show()
        }

        fun complain(ctx: Context, message: String) {
            Log.e(TAG, "**** App Error: " + message)
            CLAlert.notify(ctx, message)
        }
    }

}

