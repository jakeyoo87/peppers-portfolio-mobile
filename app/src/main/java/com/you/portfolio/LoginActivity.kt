package com.you.portfolio

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        setupListener()
    }

    private fun initView() {
        emailView = findViewById(R.id.email_login)
        passwordView = findViewById(R.id.password_login)
        loginButton = findViewById(R.id.login_button)
        signupButton = findViewById(R.id.signup_button_login)
    }

    private fun setupListener() {
        loginButton.setOnClickListener {
            (application as GlobalApplication).service
                .login(getLoginUser())
                .enqueue(loginCallback())
        }
    }

    private fun getLoginUser(): LoginUser {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        return LoginUser(username = email, password = password)
    }

    private fun loginCallback(): Callback<User> {
        return object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "가입에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "가입에 성공하였습니다.", Toast.LENGTH_LONG).show()
                    val user = response.body()
                    val token = user?.token
                    saveToken(token)
                }
            }
        }
    }

    private fun saveToken(token: String?) {
        getSharedPreferences("login", Context.MODE_PRIVATE)
            .edit()
            .putString("token", token)
            .apply()
    }
}