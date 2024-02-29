package com.example.englishstudy.activity.admin.dienkhuyet

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.viewmodel.CauDienKhuyetViewModel

class AddDienKhuyetActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var edtNoiDung: EditText
    private lateinit var edtGoiY: EditText
    private lateinit var edtDapAn: EditText
    private var idBDK = 0
    private lateinit var dienKhuyetViewModel: CauDienKhuyetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dienkhuyet)

        imgBack = findViewById(R.id.imgBackAddDK)
        imgAdd = findViewById(R.id.imgAddDK)
        edtNoiDung = findViewById(R.id.edtCauHoiAddDK)
        edtGoiY = findViewById(R.id.edtGoiYAddDK)
        edtDapAn = findViewById(R.id.edtDapAnAddDK)

        idBDK = intent.getIntExtra("idBoDienKhuyet", -1)

        imgBack.setOnClickListener {
            val intent = Intent(this@AddDienKhuyetActivity, AdminDienKhuyetActivity::class.java)
            intent.putExtra("idBoDienKhuyet", idBDK)
            startActivity(intent)
        }

        imgAdd.setOnClickListener {
            val noidung = edtNoiDung.text.toString()
            val dapan = edtDapAn.text.toString()
            val goiy = edtGoiY.text.toString()
            if (noidung == "" || dapan == "" || goiy == "") {
                Toast.makeText(
                    this@AddDienKhuyetActivity,
                    "Chưa điền đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val check = checkDapAnInGoiY(dapan, goiy)
                if (check) {
                    val result = addDienKhuyet(idBDK, noidung, dapan, goiy)
                    if (result) {
                        Toast.makeText(
                            this@AddDienKhuyetActivity,
                            "Thêm thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@AddDienKhuyetActivity, AdminDienKhuyetActivity::class.java)
                        intent.putExtra("idBoDienKhuyet", idBDK)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@AddDienKhuyetActivity,
                            "Thêm thất bại",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@AddDienKhuyetActivity,
                        "Vui lòng nhập đúng đáp án",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun addDienKhuyet(idBDK: Int, noidung: String, dapan: String, goiy: String): Boolean {
        val cauDienKhuyet = CauDienKhuyet(
            idBDK,
            noidung,
            dapan,
            goiy
        ).apply {
            this.noiDung = noidung
            this.dapan = dapan
            this.goiY = goiy
        }

        return try {
            dienKhuyetViewModel.createCauDienKhuyet(cauDienKhuyet)
            
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun checkDapAnInGoiY(dapan: String, goiy: String): Boolean {
        var goiy = goiy.replace("\\W".toRegex(), " ")
        goiy = goiy.trim { it <= ' ' }.replace("\\s{2,}".toRegex(), " ")
        val dapAn = goiy.split(" ").toTypedArray()
        return dapAn.contains(dapan)
    }
}