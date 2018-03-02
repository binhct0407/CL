package jp.couplink.app.purchase.listener

import jp.couplink.app.purchase.model.PurchaseMembership

/**
 * Created by BinhTran on 1/30/2018.
 */
interface GetPurchaseMembershipListener {
    fun onSuccess(mPurchaseMembership: PurchaseMembership)
}