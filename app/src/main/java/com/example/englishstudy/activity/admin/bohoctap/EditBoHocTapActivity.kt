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

class EditBoHocTapActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgEdit: ImageView
    private lateinit var edtBoHocTap: EditText
    private var listBHT: ArrayList<BoHocTap> = ArrayList()
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_bohoctap)
        imgBack = findViewById(R.id.imgBackEditBHT)
        imgEdit = findViewById(R.id.imgEditBHT)
        edtBoHocTap = findViewById(R.id.edtEditBoHocTap)

        val idBHT = intent.getIntExtra("ID_BHT", -1)
        val boHocTap = getBoHocTapByID(idBHT)

        edtBoHocTap.setText(boHocTap.tenBo + "")

        imgBack.setOnClickListener {
            startActivity(Intent(this@EditBoHocTapActivity, AdminBoHocTapActivity::class.java))
        }

        imgEdit.setOnClickListener {
            val ten = edtBoHocTap.text.toString()
            if (ten == "") {
                Toast.makeText(
                    this@EditBoHocTapActivity,
                    "Chưa điền đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val result = updateBoHocTap(boHocTap.id, boHocTap.stt, ten)
                if (result) {
                    Toast.makeText(
                        this@EditBoHocTapActivity,
                        "Cập nhật thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(
                        Intent(
                            this@EditBoHocTapActivity,
                            AdminBoHocTapActivity::class.java
                        )
                    )
                } else {
                    Toast.makeText(
                        this@EditBoHocTapActivity,
                        "Cập nhật thất bại",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateBoHocTap(id: Int, stt: Int, ten: String): Boolean {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        boHocTapViewModel.updateBoHocTap(id, stt, ten)
        return true
    }

    private fun getBoHocTapByID(id: Int): BoHocTap {
        boHocTapViewModel = ViewModelProvider(this).get(BoHocTapViewModel::class.java)
        val boHocTap = boHocTapViewModel.getBoHocTapDetail(id)
        return boHocTap
    }
}