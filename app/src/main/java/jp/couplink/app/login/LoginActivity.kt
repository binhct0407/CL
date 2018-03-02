package jp.couplink.app.login

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import jp.couplink.R
import jp.couplink.app.activity.FacebookLoginActivity
import jp.couplink.app.activity.MainActivity
import kotlinx.android.synthetic.main.layout_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Created by BinhTran on 12/18/2017.
 */

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        printKeyHash(this)
        login_btn_login.setOnClickListener(View.OnClickListener { login() })
        login_btn_register.setOnClickListener(View.OnClickListener { toRegister() })
        login_btn_facebooklogin.setOnClickListener(View.OnClickListener { login_facebook() })

    }


    fun login_facebook() {
        startActivity(Intent(this, FacebookLoginActivity::class.java))
    }

    fun login() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun toRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun printKeyHash(context: Activity): String? {
        val packageInfo: PackageInfo
        var key: String? = null
        try {
            //getting application package name, as defined in manifest
            val packageName = context.applicationContext.packageName

            //Retriving package info
            packageInfo = context.packageManager.getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES)

            Log.e("Package Name=", context.applicationContext.packageName)

            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                key = String(Base64.encode(md.digest(), 0))

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("Name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("No such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }

        return key
    }
}

