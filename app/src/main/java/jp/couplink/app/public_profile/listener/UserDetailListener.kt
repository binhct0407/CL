package jp.couplink.app.public_profile.listener

import jp.couplink.app.database.entities.PublicProfileObj

/**
 * Created by BinhTran on 1/30/2018.
 */
interface UserDetailListener {
    fun onSuccess(mObject: PublicProfileObj)

}