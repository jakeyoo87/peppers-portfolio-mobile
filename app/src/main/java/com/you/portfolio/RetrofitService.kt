package com.you.portfolio

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

//    @POST("user/signup/")
//    fun signup(
//        @Body signupUser: SignupUser
//    ): Call<User>

//    @POST("user/login/")
//    fun login(
//        @Body loginUser: LoginUser
//    ): Call<User>

    @POST("user/signup/")
    @FormUrlEncoded
    fun signup(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String,
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<User>
}