package jp.couplink.app.popup.listener

import jp.couplink.app.popup.model.MatchedObj

/**
 * Created by BinhTran on 3/1/2018.
 */
interface MatchedUnreadListener {
    fun onSuccess(mObject: MatchedObj)
}