package com.example.englishstudy.activity.admin.dienkhuyet

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

class AdminBoDienKhuyetActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private var listBoDienKhuyet: ArrayList<BoHocTap> = ArrayList()
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var lvBoDienKhuyet: ListView
    private var idbo = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_bodienkhuyet)
        lvBoDienKhuyet = findViewById(R.id.listviewAdminBDK)
        imgBack = findViewById(R.id.imgBackAdminBDK)

        getBoDienKhuyet()

        adapter = BoHocTapAdapter(this, R.layout.row_bodienkhuyet, listBoDienKhuyet)
        lvBoDienKhuyet.adapter = adapter
        adapter.notifyDataSetChanged()
        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        lvBoDienKhuyet.setOnItemClickListener { _, _, position, _ ->
            idbo = listBoDienKhuyet[position].id
            val intent = Intent(this, AdminDienKhuyetActivity::class.java)
            intent.putExtra("idBoDienKhuyet", idbo)
            startActivity(intent)
        }
    }

    private fun getBoDienKhuyet() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            listBoDienKhuyet.clear()
            listBoDienKhuyet.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }
}