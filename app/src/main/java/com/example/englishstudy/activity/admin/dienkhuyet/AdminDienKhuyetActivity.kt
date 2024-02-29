package com.example.englishstudy.activity.admin.dienkhuyet

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.viewmodel.CauDienKhuyetViewModel

class AdminDienKhuyetActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private var listDienKhuyet: ArrayList<CauDienKhuyet> = ArrayList()
    private lateinit var adapter: AdminDienKhuyetAdapter
    private lateinit var lvDienKhuyet: ListView
    private var idbo = 0
    private lateinit var dienKhuyetViewModel: CauDienKhuyetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dienkhuyet)

        val idIntent = intent
        idbo = idIntent.getIntExtra("idBoDienKhuyet", 0)

        lvDienKhuyet = findViewById(R.id.listviewAdminDK)
        imgBack = findViewById(R.id.imgBackAdminDK)
        imgAdd = findViewById(R.id.imgAddAdminDK)

        getDienKhuyet()

        adapter = AdminDienKhuyetAdapter(this, this, listDienKhuyet)
        lvDienKhuyet.adapter = adapter
        adapter.notifyDataSetChanged()
        imgBack.setOnClickListener {
            startActivity(Intent(this, AdminBoDienKhuyetActivity::class.java))
        }
        imgAdd.setOnClickListener {
            val intent = Intent(this, AddDienKhuyetActivity::class.java)
            intent.putExtra("idBoDienKhuyet", idbo)
            startActivity(intent)
        }
    }

    private fun getDienKhuyet() {
        dienKhuyetViewModel = ViewModelProvider(this).get(CauDienKhuyetViewModel::class.java)
        dienKhuyetViewModel.getListCauDienKhuyetByIdBo(idbo).observe(this, Observer { dienKhuyets ->
            listDienKhuyet.clear()
            listDienKhuyet.addAll(dienKhuyets)
            adapter.notifyDataSetChanged()
        })
    }
}