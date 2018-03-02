package jp.couplink.app.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import jp.couplink.R;
import kotlinx.android.synthetic.main.actionbar_login.*

/**
 * Created by BinhTran on 12/20/2017.
 */

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_register)
        back_icon.setOnClickListener(View.OnClickListener { back() })

    }

    fun back() {
        onBackPressed()
    }
}
