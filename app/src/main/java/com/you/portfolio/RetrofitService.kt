package com.you.portfolio

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @POST("user/signup/")
    fun signup(
        @Body signupUser: SignupUser
    ): Call<User>

    @POST("user/login/")
    fun login(
        @Body loginUser: LoginUser
    ): Call<User>

}