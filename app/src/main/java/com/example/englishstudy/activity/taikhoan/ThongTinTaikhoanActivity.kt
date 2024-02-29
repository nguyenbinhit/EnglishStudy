package com.example.englishstudy.activity.taikhoan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.MainActivity
import com.example.englishstudy.R
import com.example.englishstudy.activity.admin.AdminActivity
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.UserViewModel

class ThongTinTaikhoanActivity : AppCompatActivity() {
    private lateinit var imghome: ImageView
    private lateinit var tvHoten: EditText
    private lateinit var tvEmail: EditText
    private lateinit var tvSdt: EditText
    private lateinit var tvUID: EditText
    private lateinit var tvtaikhoan: TextView
    private lateinit var tvTen: TextView
    private lateinit var tvPoint: TextView
    private lateinit var btnCapNhat: Button
    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_tin_taikhoan)

        anhXa()
        getUser()

        btnCapNhat.setOnClickListener {
            updateUser()
        }

        imghome.setOnClickListener {
            val currentUserId = getCurrUserId()

            userViewModel.getUser(currentUserId).observe(this, Observer { user ->
                if (user?.role == 1) {
                    val intent = Intent(this@ThongTinTaikhoanActivity, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@ThongTinTaikhoanActivity, MainActivity::class.java)
//                    intent.putExtras(intent.extras!!)
                    startActivity(intent)
                    finish()
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun anhXa() {
        tvHoten = findViewById(R.id.textIntEdtHoten)
        tvEmail = findViewById(R.id.textIntEdtEmail)
        tvSdt = findViewById(R.id.textIntEdtSdt)
        tvUID = findViewById(R.id.textIntEdtUID)
        tvtaikhoan = findViewById(R.id.tVusername)
        tvTen = findViewById(R.id.textViewTen)
        tvPoint = findViewById(R.id.textviewPoint)
        btnCapNhat = findViewById(R.id.buttonCapNhat)
        imghome = findViewById(R.id.imgHOME)

        tvUID.isEnabled = false
        tvEmail.isEnabled = false

        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                val intent = Intent(this@ThongTinTaikhoanActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getCurrUserId(): Int {
        // Retrieve the id of the current user from shared preferences
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        return sharedPref.getInt("currentUserId", 0);
    }

    private fun getUser() {
        val currentUserId = getCurrUserId()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getUser(currentUserId).observe(this, Observer { user ->
            this.user = user

            // Update your UI with the user data here
            tvHoten.setText(user.name)
            tvTen.setText(user.name)
            tvtaikhoan.setText(user.email)
            tvPoint.setText(user.point.toString())
            tvEmail.setText(user.email)
            tvUID.setText(user.id.toString())

            if (user.role == 1) {
                countDownTimer.start()
            } else {
                countDownTimer.cancel()
            }
        })
    }

    private fun updateUser() {
        val currentUserId = getCurrUserId()

        userViewModel.getUser(currentUserId).observe(this, Observer { user ->
            // Update the user object with the new data
            user.name = tvHoten.text.toString()
            user.point = tvPoint.text.toString().toInt()
            user.email = tvEmail.text.toString()

            // Call updateUser() with the updated user
            userViewModel.updateUser(user).observe(this, Observer { success ->
                if (success) {
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                }
            })
        })
    }
}