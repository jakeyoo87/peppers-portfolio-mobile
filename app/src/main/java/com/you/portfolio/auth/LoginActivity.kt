package com.you.portfolio.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.you.portfolio.GlobalApplication
import com.you.portfolio.sample.PostActivity
import com.you.portfolio.R
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
        if ((application as GlobalApplication).isLoggedIn()) {
            startActivity(Intent(this, PostActivity::class.java))
        } else {
            setContentView(R.layout.activity_login)
            initView()
            setupListener()
        }
    }

    private fun initView() {
        emailView = findViewById(R.id.email_login)
        passwordView = findViewById(R.id.password_login)
        loginButton = findViewById(R.id.login_button)
        signupButton = findViewById(R.id.signup_button_login)
    }

    private fun setupListener() {
        loginButton.setOnClickListener {
            val email = emailView.text.toString()
            val password = passwordView.text.toString()
            (application as GlobalApplication).service
                .login(email, password)
                .enqueue(loginCallback())
        }
        signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun loginCallback(): Callback<User> {
        return object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다.", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val token = user?.token
                    (application as GlobalApplication).saveToken(token)
                    Toast.makeText(this@LoginActivity, "로그인 성공하였습니다.", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@LoginActivity, PostActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}