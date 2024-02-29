package com.example.englishstudy.activity.admin.tracnghiem

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.viewmodel.CauTracNghiemViewModel

class AddTracNghiemActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var edtNoiDung: EditText
    private lateinit var edtDapAnA: EditText
    private lateinit var edtDapAnB: EditText
    private lateinit var edtDapAnC: EditText
    private lateinit var edtDapAnD: EditText
    private lateinit var spnDapAnTrue: Spinner
    private var idBTN = 0
    private lateinit var cauTracNghiemViewModel: CauTracNghiemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tracnghiem)
        imgBack = findViewById(R.id.imgBackAddTN)
        imgAdd = findViewById(R.id.imgAddTN)
        edtNoiDung = findViewById(R.id.edtCauHoiAddTN)
        edtDapAnA = findViewById(R.id.edtDapAnAAddTN)
        edtDapAnB = findViewById(R.id.edtDapAnBAddTN)
        edtDapAnC = findViewById(R.id.edtDapAnCAddTN)
        edtDapAnD = findViewById(R.id.edtDapAnDAddTN)
        spnDapAnTrue = findViewById(R.id.spnDapAnTrueAddTN)

        val listDapAn = arrayListOf("A", "B", "C", "D")

        val dapAnAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listDapAn)
        spnDapAnTrue.adapter = dapAnAdapter
        idBTN = intent.getIntExtra("idBoTracNghiem", -1)

        imgBack.setOnClickListener {
            val intent = Intent(this, AdminTracNghiemActivity::class.java)
            intent.putExtra("idBoTracNghiem", idBTN)
            startActivity(intent)
        }

        imgAdd.setOnClickListener {
            val noidung = edtNoiDung.text.toString()
            val dapanA = edtDapAnA.text.toString()
            val dapanB = edtDapAnB.text.toString()
            val dapanC = edtDapAnC.text.toString()
            val dapanD = edtDapAnD.text.toString()
            val dapanDung = spnDapAnTrue.selectedItem.toString()
            var dapanTrue = ""
            if (noidung == "" || dapanA == "" || dapanB == "" || dapanC == "" || dapanD == "") {
                Toast.makeText(this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                when (dapanDung) {
                    "A" -> dapanTrue = "1"
                    "B" -> dapanTrue = "2"
                    "C" -> dapanTrue = "3"
                    "D" -> dapanTrue = "4"
                }
                val result =
                    addTracNghiem(idBTN, noidung, dapanA, dapanB, dapanC, dapanD, dapanTrue)
                if (result) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminTracNghiemActivity::class.java)
                    intent.putExtra("idBoTracNghiem", idBTN)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addTracNghiem(
        idbo: Int,
        noidung: String,
        dapanA: String,
        dapanB: String,
        dapanC: String,
        dapanD: String,
        dapanTrue: String
    ): Boolean {
        val cauTracNghiem = CauTracNghiem(
            idbo,
            noidung,
            dapanA,
            dapanB,
            dapanC,
            dapanD,
            dapanTrue
        )

        try {
            cauTracNghiemViewModel.createCauTracNghiem(cauTracNghiem)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}