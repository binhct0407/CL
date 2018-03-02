package jp.couplink.app.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import jp.couplink.R
import jp.couplink.app.login.LoginActivity
import jp.couplink.app.login.LoginWebViewAcitvity


/**
 * Created by BinhTran on 12/20/2017.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val i = Intent(this@SplashActivity, LoginWebViewAcitvity::class.java)
            startActivity(i)

            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private val SPLASH_TIME_OUT = 1000
    }
}
