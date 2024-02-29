package com.example.englishstudy.activity.tracnghiem

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
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

class TracNghiemActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var boHocTapArrayList: ArrayList<BoHocTap>
    private lateinit var boHocTapAdapter: BoHocTapAdapter
    private lateinit var imgback: ImageView
    private var idbocauhoi: Int = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trac_nghiem)

        listView = findViewById(R.id.lvtracnghiem)
        imgback = findViewById(R.id.imgVBackTN)
        boHocTapArrayList = ArrayList()

        addArrayBTN()

        boHocTapAdapter = BoHocTapAdapter(this, R.layout.row_botracnghiem, boHocTapArrayList)
        listView.adapter = boHocTapAdapter
        boHocTapAdapter.notifyDataSetChanged()

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
                boHocTapViewModel.getBoHocTapAtPosition(position)
                    .observe(this, Observer { boHocTap ->
                        val idbo = boHocTap.id
                        val stt = boHocTap.stt
                        val tenbo = boHocTap.tenBo
                        idbocauhoi = idbo

                        val quiz = Intent(this, QuizActivity::class.java)
                        quiz.putExtra("Bo", idbocauhoi)
                        startActivity(quiz)
                    })
            }

        imgback.setOnClickListener {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            intent.extras?.let { mainActivityIntent.putExtras(it) }
            startActivity(mainActivityIntent)
            finish()
        }
    }

    private fun addArrayBTN() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTaps ->
            boHocTapArrayList.clear()
            boHocTapArrayList.addAll(boHocTaps)
            boHocTapAdapter.notifyDataSetChanged()
        })
    }
}