package com.example.englishstudy.activity.taikhoan

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.UserViewModel
import com.example.studyenglish.activity.singletonpattern.MessageObject
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var tvDangNhap: TextView
    private lateinit var edtHoTen: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSdt: EditText
    private lateinit var edtMatKhau: EditText
    private lateinit var edtXacNhan: EditText
    private lateinit var btnSignUp: Button
    private val messageObject = MessageObject.getInstance()
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        anhXa()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        tvDangNhap = findViewById(R.id.textView_login)
        tvDangNhap.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            val hoten = edtHoTen.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val sdt = edtSdt.text.toString().trim()
            val matkhau = edtMatKhau.text.toString().trim()
            val xacnhanmatkhau = edtXacNhan.text.toString().trim()

            if (hoten == "" || email == "" || sdt == "" || matkhau == "") {
                messageObject.showDialogMessage(
                    Gravity.CENTER,
                    this@SignupActivity,
                    "Vui lòng điền đầy đủ thông tin của bạn!!",
                    0
                )
            } else {
                if (matkhau != xacnhanmatkhau) {
                    messageObject.showDialogMessage(
                        Gravity.CENTER,
                        this@SignupActivity,
                        "Xác nhận lại, mật khẩu không trùng khớp!!",
                        0
                    )

                    edtMatKhau.setText("")
                    edtXacNhan.setText("")
                } else {
                    // check tai khoan and insert user
                    userViewModel.viewModelScope.launch {
                        val checkUser = userViewModel.checkUser(email)
                        if (checkUser == null) {
                            userViewModel.insert(User(hoten, email, matkhau, 0, 0))

                            Toast.makeText(
                                this@SignupActivity,
                                "Đăng ký thành công",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            messageObject.showDialogMessage(
                                Gravity.CENTER,
                                this@SignupActivity,
                                "Email đã tồn tại!!",
                                0
                            )
                        }
                    }
                }
            }
        }
    }

    private fun anhXa() {
        tvDangNhap = findViewById(R.id.textView_login)
        edtHoTen = findViewById(R.id.editTextEmailNav)
        edtEmail = findViewById(R.id.editTextEmail)
        edtSdt = findViewById(R.id.editTextSdt)
        edtMatKhau = findViewById(R.id.editTextMatKhau)
        edtXacNhan = findViewById(R.id.editTextXacNhan)
        btnSignUp = findViewById(R.id.buttonSignUp)
    }
}