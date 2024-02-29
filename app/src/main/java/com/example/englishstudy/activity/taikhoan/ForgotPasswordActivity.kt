package com.example.englishstudy.activity.taikhoan

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.englishstudy.R
import com.example.studyenglish.activity.singletonpattern.MessageObject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var EmailF: EditText
    private lateinit var btnResetP: Button
    private val messageObject = MessageObject.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        EmailF = findViewById(R.id.etEmailForgot)
        btnResetP = findViewById(R.id.btnResetPass)

        btnResetP.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = EmailF.text.toString().trim()

        if (email.isEmpty()) {
            EmailF.error = "Hãy nhập Email của bạn!"
            EmailF.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailF.error = "Hãy nhập đúng Email!"
            EmailF.requestFocus()
        }
    }
}