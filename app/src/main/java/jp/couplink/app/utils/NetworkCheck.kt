package jp.couplink.app.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by BinhTran on 12/15/2017.
 */

object NetworkCheck {
    fun checkNetWork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info?.isConnected ?: false
    }
}
