package com.you.portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var emailView: EditText
    private lateinit var passwordView1: EditText
    private lateinit var passwordView2: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initView()
        setupListener()
    }

    private fun initView() {
        emailView = findViewById(R.id.email_signup)
        passwordView1 = findViewById(R.id.password1_signup)
        passwordView2 = findViewById(R.id.password2_signup)
        signupButton = findViewById(R.id.signup_button_signup)
    }

    private fun setupListener() {
        signupButton.setOnClickListener {
            val username = emailView.text.toString()
            val password1 = passwordView1.text.toString()
            val password2 = passwordView2.text.toString()
            (application as GlobalApplication).service
                .signup(username, password1, password2)
                .enqueue(signupCallback())
        }
    }

    private fun signupCallback(): Callback<User> {
        return object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "가입에 실패하였습니다.", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val token = user?.token
                    (application as GlobalApplication).saveToken(token)
                    Toast.makeText(this@SignupActivity, "가입에 성공하였습니다.", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(this@SignupActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}