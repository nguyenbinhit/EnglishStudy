package com.example.englishstudy.activity.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Gravity
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.MainActivity
import com.example.englishstudy.R
import com.example.englishstudy.activity.admin.bohoctap.AdminBoHocTapActivity
import com.example.englishstudy.activity.admin.dienkhuyet.AdminBoDienKhuyetActivity
import com.example.englishstudy.activity.taikhoan.LoginActivity
import com.example.englishstudy.activity.taikhoan.ThongTinTaikhoanActivity
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.UserViewModel
import com.example.englishstudy.activity.admin.luyennghe.AdminBoLuyenNgheActivity
import com.example.englishstudy.activity.admin.sapxepcau.AdminBoSapXepCauActivity
import com.example.englishstudy.activity.admin.tracnghiem.AdminBoTracNghiemActivity
import com.example.englishstudy.activity.admin.tuvung.AdminBoTuVungActivity
import com.example.studyenglish.activity.singletonpattern.MessageObject

class AdminActivity : AppCompatActivity() {
    private val messageObject = MessageObject.getInstance()
    private lateinit var adminList: ArrayList<String>
    private lateinit var adapter: AdminAdapter
    private lateinit var admins: ListView
    private lateinit var imgLogout: ImageView
    private var doubleBack = false
    private lateinit var user: User
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        admins = findViewById(R.id.listviewAdmin)
        imgLogout = findViewById(R.id.imgLogoutAdmin)

        getUser()

        adminList = ArrayList()
        adminList.add("Thông tin tài khoản")
        adminList.add("Học tập")
        adminList.add("Bộ học tập")
        adminList.add("Từ vựng")
        adminList.add("Trắc nghiệm")
        adminList.add("Sắp xếp câu")
        adminList.add("Luyện nghe")
        adminList.add("Điền khuyết")

        adapter = AdminAdapter(this, R.layout.row_admin, adminList)
        admins.adapter = adapter
        admins.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> startActivity(Intent(this, ThongTinTaikhoanActivity::class.java))
                1 -> startActivity(Intent(this, MainActivity::class.java))
                2 -> startActivity(Intent(this, AdminBoHocTapActivity::class.java))
                3 -> startActivity(Intent(this, AdminBoTuVungActivity::class.java))
                4 -> startActivity(Intent(this, AdminBoTracNghiemActivity::class.java))
                5 -> startActivity(Intent(this, AdminBoSapXepCauActivity::class.java))
                6 -> startActivity(Intent(this, AdminBoLuyenNgheActivity::class.java))
                7 -> startActivity(Intent(this, AdminBoDienKhuyetActivity::class.java))
            }
        }
        adapter.notifyDataSetChanged()

        val countDownTimer = object : CountDownTimer(3000, 3000) {
            override fun onTick(millisUntilFinished: Long) {
                messageObject.showDialogMessage(
                    Gravity.CENTER,
                    this@AdminActivity,
                    "KHÔNG THỂ TRUY CẬP!!Tài khoản của bạn không phải quản lý!!",
                    0
                )
            }

            override fun onFinish() {
                startActivity(Intent(this@AdminActivity, MainActivity::class.java))
            }
        }

        if (user.role == 1) {
            countDownTimer.start()
        }

        imgLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (doubleBack) {
            super.onBackPressed()
            finish()
            moveTaskToBack(true)
        }
        this.doubleBack = true
        Toast.makeText(applicationContext, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBack = false }, 2000)
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
            // Update your UI with the user data here
            val idUser = user.id
            val hoTen = user.name
            val point = user.point
            val email = user.email
            val role = user.role

            this.user = User(idUser.toString(), hoTen, email, point, role)
        })
    }
}