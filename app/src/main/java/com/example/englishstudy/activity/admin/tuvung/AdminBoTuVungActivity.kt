package com.example.englishstudy.activity.admin.tuvung

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

class AdminBoTuVungActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private var listBoTuVung: ArrayList<BoHocTap> = ArrayList()
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var lvBoTuVung: ListView
    private var idbo = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_botuvung)
        lvBoTuVung = findViewById(R.id.listviewAdminBTV)
        imgBack = findViewById(R.id.imgBackAdminBTV)
        getBoTuVung()
        adapter = BoHocTapAdapter(this, R.layout.row_botuvung, listBoTuVung)
        lvBoTuVung.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, AdminActivity::class.java))
        }

        lvBoTuVung.setOnItemClickListener { _, _, position, _ ->
            idbo = listBoTuVung[position].id
            val intent = Intent(this, AdminTuVungActivity::class.java)
            intent.putExtra("idBoTuVung", idbo)
            finishAffinity()
            startActivity(intent)
        }
    }

    private fun getBoTuVung() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)

        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            listBoTuVung.clear()
            listBoTuVung.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }
}