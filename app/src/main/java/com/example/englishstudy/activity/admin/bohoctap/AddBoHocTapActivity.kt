package com.example.englishstudy.activity.admin.bohoctap

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.viewmodel.BoHocTapViewModel

class AddBoHocTapActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var listBHT: ArrayList<BoHocTap>
    private lateinit var edtBoHocTap: EditText
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bohoctap)
        imgBack = findViewById(R.id.imgBackAddBHT)
        imgAdd = findViewById(R.id.imgAddBHT)
        edtBoHocTap = findViewById(R.id.edtAddBoHocTap)

        imgBack.setOnClickListener {
            startActivity(Intent(this@AddBoHocTapActivity, AdminBoHocTapActivity::class.java))
        }

        imgAdd.setOnClickListener {
            val ten = edtBoHocTap.text.toString()
            if (ten == "") {
                Toast.makeText(this@AddBoHocTapActivity, "Thêm thất bại", Toast.LENGTH_SHORT).show()
            } else {
                val max = getMaxSTTBoHocTap()
                val result = addBoHocTap(max + 1, ten)
                if (result) {
                    Toast.makeText(this@AddBoHocTapActivity, "Thêm thành công", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(
                        Intent(
                            this@AddBoHocTapActivity,
                            AdminBoHocTapActivity::class.java
                        )
                    )
                } else {
                    Toast.makeText(this@AddBoHocTapActivity, "Thêm thất bại", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun addBoHocTap(stt: Int, ten: String): Boolean {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        val boHocTap = BoHocTap(stt, ten)
        boHocTapViewModel.createBoHocTap(boHocTap)
        return true
    }

    private fun getMaxSTTBoHocTap(): Int {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        val maxSTT = boHocTapViewModel.getMaxSTTBoHocTap()
        return maxSTT
    }
}