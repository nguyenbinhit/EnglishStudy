package com.example.englishstudy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.englishstudy.activity.admin.AdminActivity
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.UserViewModel
import com.example.studyenglish.activity.singletonpattern.MessageObject
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private val messageObject = MessageObject.getInstance()
    private var doubleBack = false
    private lateinit var user: User
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUser()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Mọi thắc mắc, hỗ trợ xin vui lòng gửi mail về (nguyenbinhltv@gmail.com)",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home
        )
            .setOpenableLayout(drawer)
            .build()

        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration)
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_admin -> {
                if (user.role == 0) {
                    val intent = Intent(this@MainActivity, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    messageObject.showDialogMessage(
                        Gravity.CENTER,
                        this@MainActivity,
                        "KHÔNG THỂ TRUY CẬP!!Tài khoản của bạn không phải Quản Lý!!",
                        0
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
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

    override fun onSupportNavigateUp(): Boolean {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp()
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