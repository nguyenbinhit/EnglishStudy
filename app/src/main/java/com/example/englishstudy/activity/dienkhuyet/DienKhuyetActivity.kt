package com.example.englishstudy.activity.dienkhuyet

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

class DienKhuyetActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var imgback: ImageView
    private lateinit var boHocTapArrayList: ArrayList<BoHocTap>
    private lateinit var boHocTapAdapter: BoHocTapAdapter
    private var idbocauhoi = 0
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dien_khuyet)

        listView = findViewById(R.id.lvdienkhuyet)
        imgback = findViewById(R.id.imgVBackDK)
        boHocTapArrayList = ArrayList()
        addArrayBTN()
        boHocTapAdapter = BoHocTapAdapter(this, R.layout.row_bodienkhuyet, boHocTapArrayList)
        listView.adapter = boHocTapAdapter
        boHocTapAdapter.notifyDataSetChanged()

        imgback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtras(intent)
            startActivity(intent)
            finish()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            boHocTapViewModel.getBoHocTapAtPosition(position).observe(this, Observer { boHocTap ->
                // Update the adapter with the new data
                boHocTap?.let {
                    idbocauhoi = it.id
                    val quiz = Intent(this, FillBlanksActivity::class.java)
                    quiz.putExtra("BoDK", idbocauhoi)
                    startActivity(quiz)
                }
            })
        }
    }

    private fun addArrayBTN() {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.getListBoHocTap().observe(this, Observer { boHocTap ->
            // Update the adapter with the new data
            boHocTap?.let {
                boHocTapArrayList.clear()
                boHocTapArrayList.addAll(it)
                boHocTapAdapter.notifyDataSetChanged()
            }
        })
    }
}