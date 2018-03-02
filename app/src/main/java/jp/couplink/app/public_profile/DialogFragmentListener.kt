package jp.couplink.app.public_profile

/**
 * Created by BinhTran on 12/22/2017.
 */

interface DialogFragmentListener {
    fun ListentoDetailDialog(check: Boolean)
    fun ListentoGreetingFromDetailDialog(greeting: String)
    fun ListentoLikeDialog(check: Boolean)

}
