package jp.couplink.app.retrofit

import jp.couplink.app.database.entities.CLUser
import jp.couplink.app.database.entities.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

/**
 * Created by BinhTran on 12/9/17.
 */

interface NewcomerAPI {
    @GET("api/v2/users?filter[age]=20..31")
    fun getUsers(@Header("Authorization") header: String): Call<List<CLUser>>


    @GET("api/v2/users")
    fun searchUser(
            @QueryMap options: Map<String, String>): Call<Data>


}
