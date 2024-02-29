package com.example.englishstudy.activity.admin.luyennghe

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauLuyenNghe
import com.example.englishstudy.viewmodel.CauLuyenNgheViewModel

class AdminLuyenNgheActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private var listLuyenNghe: ArrayList<CauLuyenNghe> = ArrayList()
    private lateinit var adapter: AdminLuyenNgheAdapter
    private lateinit var lvLuyenNghe: ListView
    private var idbo = 0
    private lateinit var luyenNgheViewModel: CauLuyenNgheViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_luyennghe)
        val idIntent = intent
        idbo = idIntent.getIntExtra("idBoLuyenNghe", 0)
        lvLuyenNghe = findViewById(R.id.listviewAdminLN)
        imgBack = findViewById(R.id.imgBackAdminLN)
        imgAdd = findViewById(R.id.imgAddAdminLN)

        getLuyenNghe()

        adapter = AdminLuyenNgheAdapter(this, this, listLuyenNghe)
        lvLuyenNghe.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminBoLuyenNgheActivity::class.java))
        }

        imgAdd.setOnClickListener {
            val intent = Intent(this, AddLuyenNgheActivity::class.java)
            intent.putExtra("idBoLuyenNghe", idbo)
            startActivity(intent)
        }
    }

    private fun getLuyenNghe() {
        luyenNgheViewModel = ViewModelProvider(this).get(CauLuyenNgheViewModel::class.java)
        luyenNgheViewModel.getListCauLuyenNgheByIdBo(idbo).observe(this, Observer { luyenNghes ->
            listLuyenNghe.clear()
            listLuyenNghe.addAll(luyenNghes)
            adapter.notifyDataSetChanged()
        })
    }
}