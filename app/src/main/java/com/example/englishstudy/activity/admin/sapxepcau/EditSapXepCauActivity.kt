package com.example.englishstudy.activity.admin.sapxepcau

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.viewmodel.CauSapXepViewModel

class EditSapXepCauActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgEdit: ImageView
    private lateinit var edtNoiDung: EditText
    private lateinit var edtPart1: EditText
    private lateinit var edtPart2: EditText
    private lateinit var edtPart3: EditText
    private lateinit var edtPart4: EditText
    private var listSapXepCau: ArrayList<CauSapXep> = ArrayList()
    private lateinit var sapXepViewModel: CauSapXepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sapxepcau)
        imgBack = findViewById(R.id.imgBackEditSXC)
        imgEdit = findViewById(R.id.imgEditSXC)
        edtNoiDung = findViewById(R.id.edtCauHoiEditSXC)
        edtPart1 = findViewById(R.id.edtPart1EditSXC)
        edtPart2 = findViewById(R.id.edtPart2EditSXC)
        edtPart3 = findViewById(R.id.edtPart3EditSXC)
        edtPart4 = findViewById(R.id.edtPart4EditSXC)

        val idSXC = intent.getIntExtra("ID_SXC", -1)
        val sapxepCau = getSapXepCauByID(idSXC)
        edtNoiDung.setText(sapxepCau.dapAn)
        edtPart1.setText(sapxepCau.part1)
        edtPart2.setText(sapxepCau.part2)
        edtPart3.setText(sapxepCau.part3)
        edtPart4.setText(sapxepCau.part4)

        imgBack.setOnClickListener {
            val intent = Intent(this, AdminSapXepCauActivity::class.java)
            intent.putExtra("idBoSapXepCau", sapxepCau.idBo)
            startActivity(intent)
        }

        imgEdit.setOnClickListener {
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
                    val result = updateSapXepCau(
                        sapxepCau.id,
                        sapxepCau.idBo,
                        dapan,
                        part1,
                        part2,
                        part3,
                        part4
                    )

                    if (result) {
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, AdminSapXepCauActivity::class.java)
                        intent.putExtra("idBoSapXepCau", sapxepCau.idBo)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
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

    private fun getSapXepCauByID(id: Int): CauSapXep {
        listSapXepCau.clear()

        sapXepViewModel = ViewModelProvider(this).get(CauSapXepViewModel::class.java)

        val sapXepCau = sapXepViewModel.getCauSapXepById(id)

        listSapXepCau.add(sapXepCau)

        return listSapXepCau[0]
    }

    private fun updateSapXepCau(
        idcau: Int,
        idbo: Int,
        dapan: String,
        part1: String,
        part2: String,
        part3: String,
        part4: String
    ): Boolean {
        sapXepViewModel = ViewModelProvider(this).get(CauSapXepViewModel::class.java)

        val cauSapXep = CauSapXep(idbo, dapan, part1, part2, part3, part4).apply { this.id = idcau }

        try {
            sapXepViewModel.updateCauSapXep(cauSapXep)

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