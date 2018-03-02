package jp.couplink.app.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by BinhTran on 1/18/2018.
 */
class Login {
    private var isLogin: String? = null

    companion object {
        val BASE_URL = "http://www.couplink.jp/"

        //login with retrofit
        fun loginwithRetrofit(email: String, password: String) {
            var retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            val mLoginAPI: LoginAPI = retrofit.create(LoginAPI::class.java)
            var mService: Call<Login> = mLoginAPI.authentication(email, password)
            mService.enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                }

                override fun onFailure(call: Call<Login>?, t: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

        }

        //registration with retrofit
        fun registrationProcessWithRetrofit(email: String, password: String) {
            var retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            val mLoginAPI: LoginAPI = retrofit.create(LoginAPI::class.java)
            var mService: Call<Login> = mLoginAPI.registration(email, password)
            mService.enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFailure(call: Call<Login>?, t: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }


    }
}