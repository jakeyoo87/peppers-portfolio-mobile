package com.you.portfolio

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalApplication : Application() {

    lateinit var service: RetrofitService

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        //chrome://inspect/
        createRetrofit()
    }

    private fun createRetrofit() {
        val header = Interceptor {
            val request = it.request()
            if (isLoggedIn()) {
                getToken()?.let { token ->
                    it.proceed(
                        request.newBuilder()
                            .header("Authorization", "token $token")
                            .build()
                    )
                } ?: run {
                    it.proceed(request)
                }
            } else {
                it.proceed(request)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    private fun getToken(): String? {
        val sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE)
        return sharedPref.getString("token", null)
    }

    fun saveToken(token: String?) {
        getSharedPreferences("login", Context.MODE_PRIVATE)
            .edit()
            .putString("token", token)
            .apply()
        createRetrofit()
    }
}