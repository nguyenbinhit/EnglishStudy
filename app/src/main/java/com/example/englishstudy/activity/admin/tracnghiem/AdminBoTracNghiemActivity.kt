package com.example.englishstudy.activity.admin.tracnghiem

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.activity.admin.AdminActivity
import com.example.englishstudy.activity.bohoctap.BoHocTapAdapter
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.viewmodel.BoHocTapViewModel

class AdminBoTracNghiemActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private var listBoTracNghiem: ArrayList<BoHocTap> = ArrayList()
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var lvBoTracNghiem: ListView
    private var idbo = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_botracnghiem)
        lvBoTracNghiem = findViewById(R.id.listviewAdminBTN)
        imgBack = findViewById(R.id.imgBackAdminBTN)
        getBoTracNghiem()
        adapter = BoHocTapAdapter(this, R.layout.row_botracnghiem, listBoTracNghiem)
        lvBoTracNghiem.adapter = adapter
        adapter.notifyDataSetChanged()
        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        lvBoTracNghiem.setOnItemClickListener { _, _, position, _ ->
            idbo = listBoTracNghiem[position].id
            val intent = Intent(this, AdminTracNghiemActivity::class.java)
            intent.putExtra("idBoTracNghiem", idbo)
            startActivity(intent)
        }
    }

    private fun getBoTracNghiem() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)

        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            listBoTracNghiem.clear()
            listBoTracNghiem.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }
}