package jp.couplink.app.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Pattern;

import jp.couplink.R;
import jp.couplink.app.activity.MainActivity;
import jp.couplink.app.utils.UrlManager;

/**
 * Created by BinhTran on 1/22/2018.
 */

public class LoginWebViewAcitvity extends AppCompatActivity {

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(UrlManager.Companion.getURL_BASE() + "spa/search")) {
                String temp_cookie = "";
                String[] x = Pattern.compile(";").split(CookieManager.getInstance().getCookie(UrlManager.Companion.getURL_BASE() + "users/sign_in"));
                for (int pos = 0; pos < x.length; pos++) {
                    if (x[pos].contains("_couplink_sess")) temp_cookie = x[pos];

                }
                SharedPreferences pre = getSharedPreferences("cookie_pre", MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("login_cookie", temp_cookie);
                editor.commit();
                Intent i = new Intent(LoginWebViewAcitvity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webview.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

        }
    }

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_v2);


        webview = (WebView) findViewById(R.id.loginv2_webview);
        webview.setWebViewClient(new MyWebViewClient());


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        openURL();
        webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

    }

    /**
     * Opens the URL in a browser
     */
    private void openURL() {
        webview.loadUrl(UrlManager.Companion.getURL_BASE() + "users/sign_in");
        webview.requestFocus();
    }

    //get token
    private void openAuthLink() {
        webview.loadUrl(UrlManager.Companion.getURL_BASE() + "api/v2/auth");

    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            Log.d("samsung", html);
        }

    }
}
