package jp.couplink.app.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by BinhTran on 1/18/2018.
 */
interface LoginAPI {


    @GET("api/{email}/{password}")
    fun authentication(@Path("email") email: String, @Path("password") password: String): Call<Login>

    @POST("api/{email}/{password}")
    fun registration(@Path("email") email: String, @Path("password") password: String): Call<Login>


}