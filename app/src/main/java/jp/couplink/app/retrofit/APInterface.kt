package jp.couplink.app.retrofit

/**
 * Created by BinhTran on 1/18/2018.
 */

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APInterface {
    @GET("api/{email}/{password}")
    fun authenticate(@Path("email") email: String, @Path("password") password: String): Call<Login>

    @POST("api/{email}/{password}")
    fun registration(@Path("email") email: String, @Path("password") password: String): Call<Login>
}