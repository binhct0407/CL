package jp.couplink.app.utils

import android.content.Context
import android.os.Bundle

import com.google.firebase.analytics.FirebaseAnalytics


/**
 * Created by mizukoshiyuusuke on 2017/05/02.
 */

class FirebaseUtils(context: Context) {

    private val mFirebaseAnalytics: FirebaseAnalytics

    init {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(key: String, value: String) {
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.VALUE, value)
        mFirebaseAnalytics.logEvent(key, params)
    }

    fun logEvent(key: String, value: Int) {
        val params = Bundle()
        params.putInt(FirebaseAnalytics.Param.VALUE, value)
        mFirebaseAnalytics.logEvent(key, params)
    }

    fun logEvent(key: String, params: Bundle) {
        mFirebaseAnalytics.logEvent(key, params)
    }

    companion object {
        val PURCHASE_LOG_START = "PurchaseLogStart"
        val PURCHASE_LOG_ERROR = "PurchaseLogError"
        val PURCHASE_LOG_END = "PurchaseLogEnd"

        val NETWORK_ERROR = "NetworkError"
        val NETWORK_ERROR_PARAM_ACCESSCODE = "AccessCode"
    }
}
