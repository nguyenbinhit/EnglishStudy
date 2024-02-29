package com.example.englishstudy.activity.taikhoan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.MainActivity
import com.example.englishstudy.R
import com.example.englishstudy.activity.admin.AdminActivity
import com.example.englishstudy.activity.notify.MyService
import com.example.englishstudy.viewmodel.UserViewModel
import com.example.studyenglish.activity.singletonpattern.MessageObject
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var btnDangnhap: Button
    private lateinit var tvDangky: TextView
    private lateinit var tvforgotPassword: TextView
    private lateinit var edttaikhoan: EditText
    private lateinit var edtmatkhau: EditText
    private val messageObject: MessageObject = MessageObject.getInstance()
    private lateinit var userViewModel: UserViewModel

    companion object {
        lateinit var instance: LoginActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_login)

        instance = this

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        anhXa()

        btnDangnhap.setOnClickListener {
            val email = edttaikhoan.text.toString().trim()
            val matkhau = edtmatkhau.text.toString().trim()

            // validations for input email and password
            if (email.isEmpty()) {
                messageObject.showDialogMessage(Gravity.CENTER, this, "Vui lòng email của bạn!!", 0)
                return@setOnClickListener
            }

            if (matkhau.isEmpty()) {
                messageObject.showDialogMessage(
                    Gravity.CENTER,
                    this,
                    "Vui lòng nhập mật khẩu của bạn!!",
                    0
                )
                return@setOnClickListener
            }

            // check tai khoan
            userViewModel.viewModelScope.launch {
                val user = userViewModel.loginUser(email, matkhau)

                if (user != null) {
                    if (user.password == matkhau) {
                        // Check user role
                        val intent: Intent = if (user.role == 1) {
                            Intent(this@LoginActivity, AdminActivity::class.java)
                        } else {
                            Intent(this@LoginActivity, MainActivity::class.java)
                        }

                        // After the user has successfully logged in
                        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putInt(
                            "currentUserId",
                            user.id
                        )
                        // Assuming 'user' is your logged in User object
                        editor.apply()

                        startActivity(intent)
                    } else {
                        messageObject.showDialogMessage(
                            Gravity.CENTER,
                            this@LoginActivity,
                            "Mật khẩu không đúng!!",
                            0
                        )
                    }
                } else {
                    messageObject.showDialogMessage(
                        Gravity.CENTER,
                        this@LoginActivity,
                        "Tài khoản không tồn tại!!",
                        0
                    )
                }
            }
        }

        tvDangky.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        tvforgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun anhXa() {
        btnDangnhap = findViewById(R.id.buttonDangnhap)
        tvDangky = findViewById(R.id.textView_register)
        tvforgotPassword = findViewById(R.id.textView_forgotPassword)
        edttaikhoan = findViewById(R.id.editTextUser)
        edtmatkhau = findViewById(R.id.editTextPass)
    }

//    fun clickStopService() {
//        val intent = Intent(this, MyService::class.java)
//        stopService(intent)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        clickStopService()
//    }
}