package com.example.englishstudy.activity.taikhoan

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englishstudy.MainActivity
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.UserViewModel
import java.util.Collections

class RankingActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRank: AdapterRank
    private lateinit var tvPointrank1: TextView
    private lateinit var tvPointrank2: TextView
    private lateinit var tvPointrank3: TextView
    private lateinit var imgback: ImageView
    private lateinit var list: ArrayList<User>
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        anhXa()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        list = ArrayList()
        adapterRank = AdapterRank(this, list)
        recyclerView.adapter = adapterRank

        userViewModel.listUserRank().observe(this, Observer { users ->
            list.clear() // Clear the list before adding new users

            // Add each user to the list
            for (user in users) {
                list.add(user)
            }

            // Reverse the list
            Collections.reverse(list)

            // Update the top 3 points
            top3Point()

            // Notify the adapter that the data set has changed
            adapterRank.notifyDataSetChanged()
        })

        // Back
        imgback.setOnClickListener {
            val intent = Intent(this@RankingActivity, MainActivity::class.java)
            intent.putExtras(intent)
            startActivity(intent)
            finish()
        }
    }

    private fun anhXa() {
        recyclerView = findViewById(R.id.userranklist)
        tvPointrank1 = findViewById(R.id.tvpointrank1)
        tvPointrank2 = findViewById(R.id.tvpointrank2)
        tvPointrank3 = findViewById(R.id.tvpointrank3)
        imgback = findViewById(R.id.imgVBackRank)
    }

    private fun top3Point() {
        if (list.size >= 3) {
            tvPointrank1.text = list[0].point.toString()
            tvPointrank2.text = list[1].point.toString()
            tvPointrank3.text = list[2].point.toString()
        } else if (list.size == 2) {
            tvPointrank1.text = list[0].point.toString()
            tvPointrank2.text = list[1].point.toString()
            tvPointrank3.text = "0"
        } else if (list.size == 1) {
            tvPointrank1.text = list[0].point.toString()
            tvPointrank2.text = "0"
            tvPointrank3.text = "0"
        } else {
            tvPointrank1.text = "0"
            tvPointrank2.text = "0"
            tvPointrank3.text = "0"
        }
    }
}