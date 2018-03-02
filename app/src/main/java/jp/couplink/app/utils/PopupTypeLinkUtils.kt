package jp.couplink.app.utils

import android.app.Fragment
import android.os.Bundle
import jp.couplink.app.popup.FragmentPopupTypeLink
import jp.couplink.app.popup.model.Popup

/**
 * Created by BinhTran on 2/22/18.
 */
class PopupTypeLinkUtils {
    companion object {
        val LIKES_RECEIVE_UNREAD = "likes_receive_unread"


        val TYPE_LINK = "link"
        val TYPE_POPUP = "popup"
        fun getPopupTypeLink(mPopup: Popup): Fragment {
            var fragment: Fragment? = null
            var size: Int = mPopup.data!!.size
            for (i in 0..size - 1) {


                if (mPopup!!.data!![i].type.equals(PopupUtils.TYPE_LINK) && mPopup!!.data!![i].name.equals(LIKES_RECEIVE_UNREAD)) {
                    var bundle: Bundle? = Bundle()
                    fragment = FragmentPopupTypeLink()

                    bundle!!.putString("typelink_url", mPopup!!.data!![i].data!!.url!!)
                    fragment.arguments = bundle

                }


            }

            return fragment!!
        }
    }
}