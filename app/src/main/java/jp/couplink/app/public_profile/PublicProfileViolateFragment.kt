package jp.couplink.app.public_profile

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.couplink.R
import kotlinx.android.synthetic.main.layout_public_proflie_violate.*

/**
 * Created by BinhTran on 12/18/2017.
 */

class PublicProfileViolateFragment : Fragment() {
    internal var clicked_hide = false
    internal var clicked_block = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_public_proflie_violate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        public_profile_violate_back.setOnClickListener(View.OnClickListener { back() })
        violate_txt_hide.setOnClickListener(View.OnClickListener { violate_hide() })
        violate_txt_block.setOnClickListener(View.OnClickListener { violate_block() })
        violate_txt_doblock.setOnClickListener(View.OnClickListener { doBlockUser() })
    }

    fun back() {
        activity!!.onBackPressed()
    }

    fun violate_hide() {
        clicked_hide = !clicked_hide
        if (clicked_hide) {
            violate_txt_hide!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eye_white_16dp, 0, 0, 0)
            violate_txt_hide!!.setBackgroundResource(R.drawable.rounded_edittext_red)
            violate_txt_hide!!.setTextColor(Color.WHITE)
            violate_txt_block!!.setBackgroundResource(R.drawable.rounded_edittext_gray)
            violate_txt_block!!.setTextColor(Color.BLACK)
            clicked_block = false

        } else {
            violate_txt_hide!!.setBackgroundResource(R.drawable.rounded_edittext_gray)
            violate_txt_hide!!.setTextColor(Color.BLACK)
            violate_txt_hide!!.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eye_black_16dp, 0, 0, 0)

        }

    }

    fun violate_block() {
        clicked_block = !clicked_block
        if (clicked_block) {
            violate_txt_block!!.setBackgroundResource(R.drawable.rounded_edittext_red)
            violate_txt_block!!.setTextColor(Color.WHITE)
            violate_txt_hide!!.setBackgroundResource(R.drawable.rounded_edittext_gray)
            violate_txt_hide!!.setTextColor(Color.BLACK)
            clicked_hide = false

        } else {
            violate_txt_block!!.setBackgroundResource(R.drawable.rounded_edittext_gray)
            violate_txt_block!!.setTextColor(Color.BLACK)
        }

    }

    fun doBlockUser() {
        //do block other here
        activity!!.onBackPressed()
    }

}
