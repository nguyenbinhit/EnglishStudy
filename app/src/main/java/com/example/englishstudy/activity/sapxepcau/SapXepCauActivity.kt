package com.example.englishstudy.activity.sapxepcau

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.MainActivity
import com.example.englishstudy.R
import com.example.englishstudy.activity.bohoctap.BoHocTapAdapter
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.viewmodel.BoHocTapViewModel

class SapXepCauActivity : AppCompatActivity() {
    private lateinit var imgback: ImageView
    private var boTuVungs: ArrayList<BoHocTap> = ArrayList()
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var botuvungs: ListView
    private var idbo = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sapxepcau)
        botuvungs = findViewById(R.id.listviewSXC)
        imgback = findViewById(R.id.imgVBackSXC)

        addArrayBTV()

        adapter = BoHocTapAdapter(this, R.layout.row_bosapxepcau, boTuVungs)
        botuvungs.adapter = adapter
        adapter.notifyDataSetChanged()

        botuvungs.setOnItemClickListener { _, _, position, _ ->
            idbo = boTuVungs[position].id
            val sxc = Intent(this, ArrangeSentencesActivity::class.java)
            sxc.putExtra("idbo", idbo)
            startActivity(sxc)
        }

        imgback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtras(intent)
            startActivity(intent)
            finish()
        }
    }

    private fun addArrayBTV() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            boTuVungs.clear()
            boTuVungs.addAll(boHocTaps)
            adapter.notifyDataSetChanged()
        })
    }
}