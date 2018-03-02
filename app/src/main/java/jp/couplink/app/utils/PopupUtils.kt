package jp.couplink.app.utils

import android.app.DialogFragment
import android.os.Bundle
import jp.couplink.app.popup.FragmentPopup
import jp.couplink.app.popup.FragmentPopupTypeLink
import jp.couplink.app.popup.model.Popup

/**
 * Created by BinhTran on 2/22/18.
 */
class PopupUtils {
    companion object {
        val LIKES_RECEIVE_UNREAD="likes_receive_unread"


        val TYPE_LINK = "link"
        val TYPE_POPUP = "popup"
        fun getListDialogFragments(mPopup: Popup): ArrayList<DialogFragment> {
            var result: ArrayList<DialogFragment>? = ArrayList()
            var size: Int = mPopup.data!!.size
            for (i in 0..size - 1) {
                if (mPopup!!.data!![i].type.equals(TYPE_POPUP)) {
                    var bundle: Bundle? = Bundle()
                    bundle!!.putString("typepopup_image", mPopup!!.data!![i].data!!.image)
                    if (mPopup!!.data!![i].data!!.button!!.size > 0) {
                        bundle!!.putString("typepopup_button", mPopup!!.data!![i].data!!.button!![0].image)

                        try {
                            if (mPopup!!.data!![i].data!!.button!![0].url!!.isNotEmpty()) {
                                bundle!!.putString("typepopup_button_url", mPopup!!.data!![i].data!!.button!![0].url!!)
                            }
                        } catch (e: Exception) {

                        }


                    }


                    var fragment: DialogFragment = FragmentPopup()
                    fragment.arguments = bundle
                    result!!.add(fragment)

                }
                else
                if(mPopup!!.data!![i].type.equals(TYPE_LINK))
                {
                    var bundle:Bundle?= Bundle()
                    bundle!!.putString("typelink_url",mPopup!!.data!![i].data!!.url)
                    var fragment:DialogFragment=FragmentPopupTypeLink()
                    fragment.arguments=bundle
                    result!!.add(fragment)

                }


            }

            return result!!
        }
    }
}