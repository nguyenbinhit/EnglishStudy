package com.example.englishstudy.activity.luyennghe

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

class LuyenNgheActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var imgback: ImageView
    private lateinit var boCauHoiArrayList: ArrayList<BoHocTap>
    private lateinit var boCauHoiAdapter: BoHocTapAdapter
    private var idbocauhoi = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luyen_nghe)
        listView = findViewById(R.id.lvluyennghe)
        imgback = findViewById(R.id.imgVBackLN)
        boCauHoiArrayList = ArrayList()

        addArrayBLN()

        boCauHoiAdapter = BoHocTapAdapter(this, R.layout.row_boluyennghe, boCauHoiArrayList)
        listView.adapter = boCauHoiAdapter
        boCauHoiAdapter.notifyDataSetChanged()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedBoHocTap = boCauHoiArrayList[position]
            idbocauhoi = selectedBoHocTap.id

            val quiz = Intent(this, ListeningActivity::class.java)
            quiz.putExtra("Bo", idbocauhoi)
            startActivity(quiz)
        }

        imgback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtras(intent)
            startActivity(intent)
            finish()
        }
    }

    private fun addArrayBLN() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTapList ->
            boCauHoiArrayList.clear()
            boCauHoiArrayList.addAll(boHocTapList)
        })
    }
}