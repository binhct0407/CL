package jp.couplink.app.message

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import jp.couplink.R
import kotlinx.android.synthetic.main.layout_fragment_findevent.*

/**
 * Created by BinhTran on 1/2/2018.
 */

class FragmentFindEvent : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_fragment_findevent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load_FindEvent()
    }

    private fun load_FindEvent() {
        startWebView(find_event_webview, URL_FIND_EVENT)
        find_event_webview!!.requestFocus()
    }

    private fun startWebView(webView: WebView?, url: String) {
        webView!!.webViewClient = object : WebViewClient() {
            internal var progressDialog: ProgressDialog? = null

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onLoadResource(view: WebView, url: String) {

                if (progressDialog == null) {
                    progressDialog = ProgressDialog(context)
                    progressDialog!!.setMessage("Loading...")
                    progressDialog!!.show()
                }

            }

            override fun onPageFinished(view: WebView, url: String) {
                try {
                    if (progressDialog!!.isShowing) {
                        progressDialog!!.dismiss()
                        progressDialog = null
                    }

                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

            }

        }

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }

    companion object {
        val URL_FIND_EVENT = "https://machicon.jp/promoters/machiconjapan/?utm_source=couplink&utm_medium=referral&utm_campaign=cl_event_search_20170509"
    }

}
