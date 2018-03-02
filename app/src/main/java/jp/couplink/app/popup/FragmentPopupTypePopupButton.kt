package jp.couplink.app.popup

import android.app.DialogFragment
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import jp.couplink.R
import jp.couplink.app.utils.UrlManager
import kotlinx.android.synthetic.main.fragment_popup_button.*
import kotlinx.android.synthetic.main.fragment_popup_typelink.*

/**
 * Created by BinhTran on 2/22/2018.
 */
class FragmentPopupTypePopupButton : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popup_button, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var link_url: String = arguments!!.getString("poup_button_url")

        fragment_popup_button_webview.setWebViewClient(MyWebViewClient())


        fragment_popup_button_webview.getSettings().setJavaScriptEnabled(true)
        fragment_popup_button_webview.getSettings().setSupportZoom(true)
        fragment_popup_button_webview.getSettings().setBuiltInZoomControls(true)
        fragment_popup_button_webview.loadUrl(link_url)
        fragment_popup_button_webview.requestFocus()
        fragment_popup_button_webview.addJavascriptInterface(MyJavaScriptInterface(activity), "HtmlViewer")

    }


    internal inner class MyJavaScriptInterface(private val ctx: Context) {

        @JavascriptInterface
        fun showHTML(html: String) {
            Log.d("samsung", html)
        }

    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
         //   fragment_popup_button_webview.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")

        }
    }
}