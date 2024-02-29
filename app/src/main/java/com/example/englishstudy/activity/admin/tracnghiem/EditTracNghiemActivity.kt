package com.example.englishstudy.activity.admin.tracnghiem

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.viewmodel.CauTracNghiemViewModel

class EditTracNghiemActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgEdit: ImageView
    private lateinit var edtNoiDung: EditText
    private lateinit var edtDapAnA: EditText
    private lateinit var edtDapAnB: EditText
    private lateinit var edtDapAnC: EditText
    private lateinit var edtDapAnD: EditText
    private lateinit var spnDapAnTrue: Spinner
    private var listTracNghiem: ArrayList<CauTracNghiem> = ArrayList()
    private lateinit var tracNghiemViewModel: CauTracNghiemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tracnghiem)
        imgBack = findViewById(R.id.imgBackEditTN)
        imgEdit = findViewById(R.id.imgEditTN)
        edtNoiDung = findViewById(R.id.edtCauHoiEditTN)
        edtDapAnA = findViewById(R.id.edtDapAnAEditTN)
        edtDapAnB = findViewById(R.id.edtDapAnBEditTN)
        edtDapAnC = findViewById(R.id.edtDapAnCEditTN)
        edtDapAnD = findViewById(R.id.edtDapAnDEditTN)
        spnDapAnTrue = findViewById(R.id.spnDapAnTrueEditTN)

        val listDapAn = arrayListOf("A", "B", "C", "D")
        val dapAnAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listDapAn)
        spnDapAnTrue.adapter = dapAnAdapter

        val idTN = intent.getIntExtra("ID_TN", -1)
        val tracNghiem = getTracNghiemByID(idTN)

        edtNoiDung.setText(tracNghiem.noiDung)
        edtDapAnA.setText(tracNghiem.dapAnA)
        edtDapAnB.setText(tracNghiem.dapAnB)
        edtDapAnC.setText(tracNghiem.dapAnC)
        edtDapAnD.setText(tracNghiem.dapAnD)
        when (tracNghiem.dapAnDung) {
            "1" -> spnDapAnTrue.setSelection(0)
            "2" -> spnDapAnTrue.setSelection(1)
            "3" -> spnDapAnTrue.setSelection(2)
            "4" -> spnDapAnTrue.setSelection(3)
        }
        imgBack.setOnClickListener {
            val intent = Intent(this, AdminTracNghiemActivity::class.java)
            intent.putExtra("idBoTracNghiem", tracNghiem.idBo)
            startActivity(intent)
        }
        imgEdit.setOnClickListener {
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
                val result = updateTracNghiem(
                    tracNghiem.id,
                    tracNghiem.idBo,
                    noidung,
                    dapanA,
                    dapanB,
                    dapanC,
                    dapanD,
                    dapanTrue
                )
                if (result) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminTracNghiemActivity::class.java)
                    intent.putExtra("idBoTracNghiem", tracNghiem.idBo)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getTracNghiemByID(id: Int): CauTracNghiem {
        listTracNghiem.clear()

        tracNghiemViewModel = ViewModelProvider(this).get(CauTracNghiemViewModel::class.java)

        val tracNghiem = tracNghiemViewModel.getCauTracNghiemDetail(id)

        listTracNghiem.add(tracNghiem)

        return listTracNghiem[0]
    }

    private fun updateTracNghiem(
        idcau: Int,
        idbo: Int,
        noidung: String,
        dapanA: String,
        dapanB: String,
        dapanC: String,
        dapanD: String,
        dapanTrue: String
    ): Boolean {
        tracNghiemViewModel = ViewModelProvider(this).get(CauTracNghiemViewModel::class.java)

        val tracNghiem = CauTracNghiem(
            idbo,
            noidung,
            dapanA,
            dapanB,
            dapanC,
            dapanD,
            dapanTrue
        ).apply { id = idcau }

        try {
            tracNghiemViewModel.updateCauTracNghiem(tracNghiem)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}