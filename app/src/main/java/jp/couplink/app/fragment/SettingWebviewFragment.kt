package jp.couplink.app.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient

import jp.couplink.R
import jp.couplink.app.activity.MainActivity
import jp.couplink.app.listener.SettingChooseListener
import jp.couplink.app.purchase.coin.FragmentCoinPage
import jp.couplink.app.purchase.membership.FragmentMembership
import jp.couplink.app.utils.UrlManager
import kotlinx.android.synthetic.main.fragment_setting_webview.*

/**
 * Created by BinhTran on 1/22/2018.
 */

class SettingWebviewFragment : Fragment() {

    private var mListener: OnSettingWebViewActionListener? = null
    private var mSettingChooseListner: SettingChooseListener? = null

    companion object {
        fun newInstance(): SettingWebviewFragment {
            val fragment = SettingWebviewFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }


    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            if (url.contains(UrlManager.URL_BASE+"user_lists/favorite_list")) {
                mSettingChooseListner!!.onSelectedSetting(6)
            }
            if (url.contains(UrlManager.URL_BASE+"user_lists/received_like_list")) {
                mSettingChooseListner!!.onSelectedSetting(4)
            }

            if (url.contains(UrlManager.URL_BASE+"user_lists/sent_like_list")) {
                mSettingChooseListner!!.onSelectedSetting(5)
            }
            if (url.contains(UrlManager.URL_BASE+"payment/membership")) {

                var child_fragment: FragmentMembership
                child_fragment = FragmentMembership()

                fragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_setting_webview_contain_setting, child_fragment)
                        .addToBackStack(null)
                        .commit()
            }
            if (url.contains(UrlManager.URL_BASE+"payment/couplinkcoin")) {

                var child_fragment: FragmentCoinPage
                child_fragment = FragmentCoinPage()

                fragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_setting_webview_contain_setting, child_fragment)
                        .addToBackStack(null)
                        .commit()
            }
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            // fragment_setting_webview.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")

        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSettingWebViewActionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFavoriteActionListener")
        }
        if (context is SettingChooseListener) {
            mSettingChooseListner = context

        }


    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        mSettingChooseListner = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting_webview, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_setting_webview.webViewClient = MyWebViewClient()


        fragment_setting_webview.settings.javaScriptEnabled = true
        fragment_setting_webview.settings.setSupportZoom(true)
        fragment_setting_webview.settings.builtInZoomControls = true
        openURL()
        fragment_setting_webview.addJavascriptInterface(MyJavaScriptInterface(context!!), "HtmlViewer")
    }


    /**
     * Opens the URL in a browser
     */
    private fun openURL() {
        fragment_setting_webview.loadUrl("https://dev.couplink.jp/spa/tutorial")
      //  fragment_setting_webview.loadUrl(UrlManager.URL_BASE+"user_page/mypage")
        fragment_setting_webview.requestFocus()
    }

    //get token
    private fun openAuthLink() {
        fragment_setting_webview.loadUrl(UrlManager.URL_BASE+"api/v2/auth")

    }

    interface OnSettingWebViewActionListener {
        // TODO: Update argument type and name
        fun OnSettingWebViewAction(uri: Uri)
    }

    internal inner class MyJavaScriptInterface(private val ctx: Context) {

        @JavascriptInterface
        fun showHTML(html: String) {
            Log.d("samsung", html)
        }

    }
}
