package com.you.portfolio

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    class User

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") usernmae: String,
        @Field("password1") password1: String,
        @Field("password2") pasword2: String
    ): Call<User>


    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

}