package com.example.englishstudy.activity.admin.tuvung

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.viewmodel.TuVungViewMModel

class AdminTuVungActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private var listTuVung: ArrayList<TuVung> = ArrayList()
    private lateinit var adapter: AdminTuVungAdapter
    private lateinit var lvTuVung: ListView
    private var idbo = 0
    private lateinit var tuVungViewMModel: TuVungViewMModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tuvung)
        val idIntent = intent
        idbo = idIntent.getIntExtra("idBoTuVung", 0)
        lvTuVung = findViewById(R.id.listviewAdminTV)
        imgBack = findViewById(R.id.imgBackAdminTV)
        imgAdd = findViewById(R.id.imgAddAdminTV)

        getTuVung()

        adapter = AdminTuVungAdapter(this, this, listTuVung)
        lvTuVung.adapter = adapter
        adapter.notifyDataSetChanged()

        imgBack.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, AdminBoTuVungActivity::class.java))
        }

        imgAdd.setOnClickListener {
            val intent = Intent(this, AddTuVungActivity::class.java)
            intent.putExtra("idBoTuVung", idbo)
            finishAffinity()
            startActivity(intent)
        }
    }

    private fun getTuVung() {
        tuVungViewMModel = ViewModelProvider(this).get(TuVungViewMModel::class.java)

        tuVungViewMModel.getListTuVungByIdBo(idbo).observe(this, Observer { tuVungs ->
            listTuVung.clear()
            listTuVung.addAll(tuVungs)
            adapter.notifyDataSetChanged()
        })
    }
}