package com.example.englishstudy.activity.admin.sapxepcau

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.viewmodel.CauSapXepViewModel

class AddSapXepCauActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var edtNoiDung: EditText
    private lateinit var edtPart1: EditText
    private lateinit var edtPart2: EditText
    private lateinit var edtPart3: EditText
    private lateinit var edtPart4: EditText
    private var idBSXC = 0
    private lateinit var cauSapXepViewModel: CauSapXepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sapxepcau)
        imgBack = findViewById(R.id.imgBackAddSXC)
        imgAdd = findViewById(R.id.imgAddSXC)
        edtNoiDung = findViewById(R.id.edtCauHoiAddSXC)
        edtPart1 = findViewById(R.id.edtPart1AddSXC)
        edtPart2 = findViewById(R.id.edtPart2AddSXC)
        edtPart3 = findViewById(R.id.edtPart3AddSXC)
        edtPart4 = findViewById(R.id.edtPart4AddSXC)
        idBSXC = intent.getIntExtra("idBoSapXepCau", -1)

        imgBack.setOnClickListener {
            val intent = Intent(this, AdminSapXepCauActivity::class.java)
            intent.putExtra("idBoSapXepCau", idBSXC)
            startActivity(intent)
        }

        imgAdd.setOnClickListener {
            val dapan = edtNoiDung.text.toString()
            val part1 = edtPart1.text.toString()
            val part2 = edtPart2.text.toString()
            val part3 = edtPart3.text.toString()
            val part4 = edtPart4.text.toString()
            if (dapan == "" || part1 == "" || part2 == "" || part3 == "" || part4 == "") {
                Toast.makeText(this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                val check = checkPartInResult(dapan, part1, part2, part3, part4)
                if (check) {
                    val result = addSapXepCau(idBSXC, dapan, part1, part2, part3, part4)
                    if (result) {
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, AdminSapXepCauActivity::class.java)
                        intent.putExtra("idBoSapXepCau", idBSXC)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Vui lòng nhập đúng nội dung tách của câu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun addSapXepCau(
        idbo: Int,
        dapan: String,
        part1: String,
        part2: String,
        part3: String,
        part4: String
    ): Boolean {
        val cauSapXep = CauSapXep(
            idbo,
            dapan,
            part1,
            part2,
            part3,
            part4
        )

        try {
            cauSapXepViewModel.createCauSapXep(cauSapXep)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun checkPartInResult(
        dapan: String,
        part1: String,
        part2: String,
        part3: String,
        part4: String
    ): Boolean {
        val part = listOf(part1, part2, part3, part4).joinToString(" ")
        return dapan == part
    }
}