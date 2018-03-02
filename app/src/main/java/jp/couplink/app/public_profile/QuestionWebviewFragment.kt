package jp.couplink.app.public_profile

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_question_webview.*

/**
 * Created by BinhTran on 1/22/2018.
 */

class QuestionWebviewFragment : Fragment() {


    companion object {
        fun newInstance(): QuestionWebviewFragment {
            val fragment = QuestionWebviewFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }


    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            //   view.loadUrl(url)
            // fragment_setting_webview.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question_webview, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_questions_webview.webViewClient = MyWebViewClient()


        fragment_questions_webview.settings.javaScriptEnabled = true
        fragment_questions_webview.settings.setSupportZoom(true)
        fragment_questions_webview.settings.builtInZoomControls = true
        fragment_questions_webview.settings.domStorageEnabled = true
        openURL()
        fragment_questions_webview.addJavascriptInterface(MyJavaScriptInterface(context!!), "HtmlViewer")
    }


    /**
     * Opens the URL in a browser
     */
    private fun openURL() {
        var currentProfileID: String? = ""
        var bundle: Bundle? = this!!.arguments
        if (bundle != null) {
            currentProfileID = bundle!!.getInt("currentProfileId").toString()
        }
        var url: String = "http://13.113.80.129:3000/spa/users/" + currentProfileID + "/ask_question"
        fragment_questions_webview.loadUrl(url)
        //fragment_messaging_webview.requestFocus()
    }

    //get token
    private fun openAuthLink() {
        fragment_questions_webview.loadUrl("http://13.113.80.129:3000/api/v2/auth")

    }

    internal inner class MyJavaScriptInterface(private val ctx: Context) {

        @JavascriptInterface
        fun showHTML(html: String) {
            Log.d("samsung", html)
        }

    }
}
