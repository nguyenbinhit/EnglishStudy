package com.example.englishstudy.activity.admin.sapxepcau

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

class AdminBoSapXepCauActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var lvBoSapXepCau: ListView
    private var listBoSapXepCau: ArrayList<BoHocTap> = ArrayList()
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_bosapxepcau)
        lvBoSapXepCau = findViewById(R.id.listviewAdminBSXC)
        imgBack = findViewById(R.id.imgBackAdminBSXC)
        getBoSapXepCau()
        adapter = BoHocTapAdapter(this, R.layout.row_bosapxepcau, listBoSapXepCau)
        lvBoSapXepCau.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        lvBoSapXepCau.setOnItemClickListener { _, _, position, _ ->
            val idbo = listBoSapXepCau[position].id
            val intent = Intent(this, AdminSapXepCauActivity::class.java)
            intent.putExtra("idBoSapXepCau", idbo)
            startActivity(intent)
        }
    }

    private fun getBoSapXepCau() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            listBoSapXepCau.clear()
            listBoSapXepCau.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }
}