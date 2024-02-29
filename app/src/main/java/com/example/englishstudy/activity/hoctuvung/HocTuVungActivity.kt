package com.example.englishstudy.activity.hoctuvung

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

class HocTuVungActivity : AppCompatActivity() {
    private lateinit var imgback: ImageView
    private lateinit var boTuVungs: ArrayList<BoHocTap>
    private lateinit var adapter: BoHocTapAdapter
    private lateinit var botuvungs: ListView
    private var idbo = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoctuvung)
        botuvungs = findViewById(R.id.listviewHTV)
        imgback = findViewById(R.id.imgVBackHTV)
        boTuVungs = ArrayList()

        addArrayBTV()

        adapter = BoHocTapAdapter(this, R.layout.row_botuvung, boTuVungs)
        botuvungs.adapter = adapter
        adapter.notifyDataSetChanged()

        botuvungs.setOnItemClickListener { _, _, position, _ ->
            idbo = boTuVungs[position].id
            val dstv = Intent(this, DSTuVungActivity::class.java)
            dstv.putExtra("idbo", idbo)
            startActivity(dstv)
        }

        imgback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addArrayBTV() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer {
            boTuVungs.clear()
            boTuVungs.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }
}