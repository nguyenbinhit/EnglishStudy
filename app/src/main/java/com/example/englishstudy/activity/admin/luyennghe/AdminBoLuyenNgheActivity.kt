package com.example.englishstudy.activity.admin.luyennghe

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

class AdminBoLuyenNgheActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private var listBoLuyenNghe: ArrayList<BoHocTap> = ArrayList()
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var lvBoLuyenNghe: ListView
    private var idbo = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_boluyennghe)
        lvBoLuyenNghe = findViewById(R.id.listviewAdminBLN)
        imgBack = findViewById(R.id.imgBackAdminBLN)

        getBoLuyenNghe()

        adapter = BoHocTapAdapter(this, R.layout.row_boluyennghe, listBoLuyenNghe)
        lvBoLuyenNghe.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        lvBoLuyenNghe.setOnItemClickListener { _, _, position, _ ->
            idbo = listBoLuyenNghe[position].id
            val intent = Intent(this, AdminLuyenNgheActivity::class.java)
            intent.putExtra("idBoLuyenNghe", idbo)
            startActivity(intent)
        }
    }

    private fun getBoLuyenNghe() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            listBoLuyenNghe.clear()
            listBoLuyenNghe.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }
}