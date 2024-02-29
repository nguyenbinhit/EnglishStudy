package com.example.englishstudy.activity.admin.sapxepcau

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.viewmodel.CauSapXepViewModel

class AdminSapXepCauActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private var listSapXepCau: ArrayList<CauSapXep> = ArrayList()
    private lateinit var adapter: AdminSapXepCauAdapter
    private lateinit var lvSapXepCau: ListView
    private var idbo = 0
    private lateinit var sapXepViewModel: CauSapXepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_sapxepcau)
        val idIntent = intent
        idbo = idIntent.getIntExtra("idBoSapXepCau", 0)
        lvSapXepCau = findViewById(R.id.listviewAdminSXC)
        imgBack = findViewById(R.id.imgBackAdminSXC)
        imgAdd = findViewById(R.id.imgAddAdminSXC)
        getSapXepCau()
        adapter = AdminSapXepCauAdapter(this, this, listSapXepCau)
        lvSapXepCau.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminBoSapXepCauActivity::class.java))
        }

        imgAdd.setOnClickListener {
            val intent = Intent(this, AddSapXepCauActivity::class.java)
            intent.putExtra("idBoSapXepCau", idbo)
            startActivity(intent)
        }
    }

    private fun getSapXepCau() {
        sapXepViewModel = ViewModelProvider(this).get(CauSapXepViewModel::class.java)
        sapXepViewModel.getListCauSapXepByIdBo(idbo).observe(this, Observer { sapXeps ->
            listSapXepCau.clear()
            listSapXepCau.addAll(sapXeps)
            adapter.notifyDataSetChanged()
        })
    }
}