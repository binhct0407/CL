/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.couplink.app.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import jp.couplink.R
import kotlinx.android.synthetic.main.activity_facebook.*

/**
 * Demonstrate Firebase Authentication using a Facebook access token.
 */
class FacebookLoginActivity : AppCompatActivity(), View.OnClickListener {
    var current_token: String = ""
    internal var dialog: ProgressDialog? = null

    private var mStatusTextView: TextView? = null
    private var mDetailTextView: TextView? = null

    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null
    // [END declare_auth]

    private var mCallbackManager: CallbackManager? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_facebook)

        // Views

        mStatusTextView = findViewById(R.id.status)
        mDetailTextView = findViewById(R.id.detail)
        findViewById<View>(R.id.button_facebook_signout).setOnClickListener(this)

        // [START initialize_auth]
        // Initialize Firebase Auth
        btn_load_web.setOnClickListener(View.OnClickListener { load_FindEvent() })

        FirebaseApp.initializeApp(applicationContext)
        mAuth = FirebaseAuth.getInstance(FirebaseApp.initializeApp(applicationContext)!!)
        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create()
        val loginButton = findViewById<LoginButton>(R.id.button_facebook_login)
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult)
                handleFacebookAccessToken(loginResult.accessToken)
                current_token = loginResult.accessToken.toString()
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }


            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        })
        // [END initialize_fblogin]
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START on_activity_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
    }
    // [END on_activity_result]

    // [START auth_with_facebook]

    private fun showProgressDialog() {
        dialog = ProgressDialog(this)
        dialog!!.show()
    }

    private fun hideProgressDialog() {
        if (dialog != null) dialog!!.dismiss()
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)
        // [START_EXCLUDE silent]
        showProgressDialog()
        // [END_EXCLUDE]

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this@FacebookLoginActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog()
                    // [END_EXCLUDE]
                }
    }
    // [END auth_with_facebook]

    fun signOut() {
        mAuth!!.signOut()
        LoginManager.getInstance().logOut()

        updateUI(null)
    }

    private fun updateUI(user: FirebaseUser?) {
        hideProgressDialog()


        //go to mainActivity
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        /*
        if (user != null) {

            mStatusTextView!!.text = getString(R.string.facebook_status_fmt, user.displayName)
            mDetailTextView!!.text = getString(R.string.firebase_status_fmt, user.uid)

            findViewById<View>(R.id.button_facebook_login).visibility = View.GONE
            findViewById<View>(R.id.button_facebook_signout).visibility = View.VISIBLE
        } else {
            mStatusTextView!!.setText(R.string.signed_out)
            mDetailTextView!!.text = null

            findViewById<View>(R.id.button_facebook_login).visibility = View.VISIBLE
            findViewById<View>(R.id.button_facebook_signout).visibility = View.GONE
        }
        */
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.button_facebook_signout) {
            signOut()
        }
    }

    companion object {
        val URL_FIND_EVENT = "https:/www.facebook.com"
        val TAG = "FacebookLogin"
    }


    fun load_FindEvent() {
        facebook_webview.settings.javaScriptEnabled = true
        facebook_webview.loadUrl(URL_FIND_EVENT)
        facebook_webview.loadUrl("https://www.facebook.com/?access_token=" + current_token);

    }

}
