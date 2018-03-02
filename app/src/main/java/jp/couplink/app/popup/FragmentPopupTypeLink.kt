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
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import jp.couplink.R
import jp.couplink.app.like_thankyou.LikeThankyouDialogFragment
import jp.couplink.app.utils.UrlManager
import kotlinx.android.synthetic.main.fragment_popup_typelink.*

/**
 * Created by BinhTran on 2/22/2018.
 */
class FragmentPopupTypeLink : DialogFragment() {

    companion object {
        fun newInstance(): FragmentPopupTypeLink {
            val fragment = FragmentPopupTypeLink()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()


    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            // fragment_popup_typelink_webview.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popup_typelink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var link_url: String = arguments!!.getString("typelink_url")


    /*
        if (link_url.contains(UrlManager.URL_LIKE_RECEIVE_UNREAD)) {
            var fragment = LikeThankyouDialogFragment()

            //   fragment.show(fragmentManager, "popup_link")
            fragmentManager.beginTransaction().replace(R.id.fragment_search_main, fragment,"tagLikeUnread").addToBackStack(null).commit()


        } else
*/
            if (link_url.contains(UrlManager.URL_MATCHED_UNREAD)) {

                var fragment = FragmentMatchedUnread()

                //   fragment.show(fragmentManager, "popup_link")
                fragmentManager.beginTransaction().replace(R.id.fragment_search_main, fragment,"tagMatchedUnread").addToBackStack(null).commit()

            } else {


                fragment_popup_typelink_webview.webChromeClient = WebChromeClient()

                fragment_popup_typelink_webview.settings.setJavaScriptEnabled(true)
                fragment_popup_typelink_webview.settings.setSupportZoom(true)
                fragment_popup_typelink_webview.settings.builtInZoomControls = true
                fragment_popup_typelink_webview.loadUrl(link_url)
                fragment_popup_typelink_webview.requestFocus()
                fragment_popup_typelink_webview.addJavascriptInterface(MyJavaScriptInterface(activity!!), "HtmlViewer")


            }


    }


    internal inner class MyJavaScriptInterface(private val ctx: Context) {

        @JavascriptInterface
        fun showHTML(html: String) {
            Log.d("samsung", html)
        }

    }
}