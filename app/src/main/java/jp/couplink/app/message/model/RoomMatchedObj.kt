package jp.couplink.app.message.model

import com.google.gson.annotations.SerializedName
import jp.couplink.app.mocking.UserObject

/**
 * Created by BinhTran on 2/7/2018.
 */
class RoomMatchedObj {
    @SerializedName("data")
    var data: Array<UserObject>? = null
    
}