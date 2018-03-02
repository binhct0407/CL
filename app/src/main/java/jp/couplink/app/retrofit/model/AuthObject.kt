package jp.couplink.app.retrofit.model

import jp.couplink.app.retrofit.UserLogin

/**
 * Created by BinhTran on 1/24/2018.
 */
class AuthObject {
    var access_token: String? = null
    var token_type: String? = null
    var expired_in: String? = null
    var refresh_token: String? = null
    var id_token: String? = null
    var user: UserLogin? = null

}