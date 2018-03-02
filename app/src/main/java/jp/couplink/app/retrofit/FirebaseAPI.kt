package jp.couplink.app.retrofit

import java.util.HashMap

import jp.couplink.app.database.entities.FriendChat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by BinhTran on 12/12/2017.
 */

interface FirebaseAPI {
    @get:GET("users.json")
    val friendChats: Call<HashMap<String, FriendChat>>

    @GET("/users.json")
    fun getMatchedUsers(@QueryMap option: Map<String, String>): Call<List<FriendChat>>

    companion object {
        val FIREBASE_URL = "https://realtimemessage-42194.firebaseio.com/"
    }
}
