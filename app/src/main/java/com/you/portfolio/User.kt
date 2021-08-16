package com.you.portfolio

import java.io.Serializable

class User(
    var username: String? = null,
    var token: String? = null,
) : Serializable

class SignupUser(
    var username: String? = null,
    var password1: String? = null,
    var password2: String? = null,
) : Serializable

class LoginUser(
    var username: String? = null,
    var password: String? = null,
) : Serializable
