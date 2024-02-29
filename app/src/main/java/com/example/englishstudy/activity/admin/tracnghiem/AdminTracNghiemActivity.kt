package com.example.englishstudy.activity.admin.tracnghiem

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.viewmodel.CauTracNghiemViewModel

class AdminTracNghiemActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private var listTracNghiem: ArrayList<CauTracNghiem> = ArrayList()
    private lateinit var adapter: AdminTracNghiemAdapter
    private lateinit var lvTracNghiem: ListView
    private var idbo = 0
    private lateinit var tracNghiemViewModel: CauTracNghiemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tracnghiem)
        val idIntent = intent
        idbo = idIntent.getIntExtra("idBoTracNghiem", 0)
        lvTracNghiem = findViewById(R.id.listviewAdminTN)
        imgBack = findViewById(R.id.imgBackAdminTN)
        imgAdd = findViewById(R.id.imgAddAdminTN)
        getTracNghiem()
        adapter = AdminTracNghiemAdapter(this, this, listTracNghiem)
        lvTracNghiem.adapter = adapter
        adapter.notifyDataSetChanged()
        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminBoTracNghiemActivity::class.java))
        }
        imgAdd.setOnClickListener {
            val intent = Intent(this, AddTracNghiemActivity::class.java)
            intent.putExtra("idBoTracNghiem", idbo)
            startActivity(intent)
        }
    }

    private fun getTracNghiem() {
        tracNghiemViewModel = ViewModelProvider(this).get(CauTracNghiemViewModel::class.java)
        tracNghiemViewModel.getListCauTracNghiemByIdBo(idbo).observe(this, Observer { tracNghiems ->
            listTracNghiem.clear()
            listTracNghiem.addAll(tracNghiems)
            adapter.notifyDataSetChanged()
        })
    }
}