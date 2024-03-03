package com.example.englishstudy.activity.admin.bohoctap

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.activity.admin.AdminActivity
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.viewmodel.BoHocTapViewModel

class AdminBoHocTapActivity : AppCompatActivity(), OnBoHocTapDeleteListener {
    private lateinit var listBHT: MutableList<BoHocTap> // Change List to MutableList
    private lateinit var adapter: AdminBoHocTapAdapter
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var listViewBHT: ListView
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_bohoctap)

        listBHT = ArrayList<BoHocTap>()

        listViewBHT = findViewById(R.id.listviewAdminBHT)
        imgBack = findViewById(R.id.imgBackAdminBHT)
        imgAdd = findViewById(R.id.imgAddBHT)

        getBoHocTap()

        adapter = AdminBoHocTapAdapter(this, this, listBHT as ArrayList<BoHocTap>, this)
        listViewBHT.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        imgAdd.setOnClickListener {
            startActivity(Intent(this, AddBoHocTapActivity::class.java))
        }
    }

    private fun getBoHocTap() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            listBHT.clear()
            listBHT.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDeleteBoHocTap(boHocTap: BoHocTap) {
        boHocTapViewModel.deleteBoHocTap(boHocTap)
    }
}