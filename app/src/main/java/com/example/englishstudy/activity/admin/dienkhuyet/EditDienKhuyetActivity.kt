package com.example.englishstudy.activity.admin.dienkhuyet

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.viewmodel.CauDienKhuyetViewModel

class EditDienKhuyetActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgEdit: ImageView
    private lateinit var edtNoiDung: EditText
    private lateinit var edtGoiY: EditText
    private lateinit var edtDapAn: EditText
    private var idDK = 0
    private lateinit var dienKhuyetViewModel: CauDienKhuyetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_dienkhuyet)
        imgBack = findViewById(R.id.imgBackEditDK)
        imgEdit = findViewById(R.id.imgEditDK)
        edtNoiDung = findViewById(R.id.edtCauHoiEditDK)
        edtGoiY = findViewById(R.id.edtGoiYEditDK)
        edtDapAn = findViewById(R.id.edtDapAnEditDK)
        idDK = intent.getIntExtra("ID_DK", -1)

        val dk = getDienKhuyetByID(idDK)

        edtNoiDung.setText(dk.noiDung)
        edtGoiY.setText(dk.goiY)
        edtDapAn.setText(dk.dapAn)
        imgBack.setOnClickListener {
            val intent = Intent(this@EditDienKhuyetActivity, AdminDienKhuyetActivity::class.java)
            intent.putExtra("idBoDienKhuyet", dk.idBo)
            startActivity(intent)
        }
        imgEdit.setOnClickListener {
            val noidung = edtNoiDung.text.toString()
            val dapan = edtDapAn.text.toString()
            val goiy = edtGoiY.text.toString()
            if (noidung == "" || dapan == "" || goiy == "") {
                Toast.makeText(
                    this@EditDienKhuyetActivity,
                    "Chưa điền đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val check = checkDapAnInGoiY(dapan, goiy)
                if (check) {
                    val result = updateDienKhuyet(dk.id, dk.idBo, noidung, dapan, goiy)
                    if (result) {
                        Toast.makeText(
                            this@EditDienKhuyetActivity,
                            "Cập nhật thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@EditDienKhuyetActivity, AdminDienKhuyetActivity::class.java)
                        intent.putExtra("idBoDienKhuyet", dk.idBo)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@EditDienKhuyetActivity,
                            "Cập nhật thất bại",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditDienKhuyetActivity,
                        "Vui lòng nhập đúng đáp án",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getDienKhuyetByID(id: Int) : CauDienKhuyet {
        var cauDienKhuyet = CauDienKhuyet(0, "", "", "")

        dienKhuyetViewModel = ViewModelProvider(this).get(CauDienKhuyetViewModel::class.java)

        dienKhuyetViewModel.getCauDienKhuyetById(id).observe(this, Observer { dienKhuyet ->
            val idcau = dienKhuyet.id
            val idbo = dienKhuyet.idBo
            val noidung = dienKhuyet.noiDung
            val dapan = dienKhuyet.dapAn
            val goiy = dienKhuyet.goiY

            if (noidung != null && goiy != null) {
                cauDienKhuyet = CauDienKhuyet(idbo, noidung, dapan, goiy).apply {
                    this.id = idcau
                }
            }
        })

        return cauDienKhuyet
    }

    private fun updateDienKhuyet(
        idcau: Int,
        idbo: Int,
        noidung: String,
        dapan: String,
        goiy: String
    ): Boolean {
        val cauDienKhuyet = CauDienKhuyet(idbo, noidung, dapan, goiy).apply {
            this.id = idcau
        }

        return dienKhuyetViewModel.updateCauDienKhuyet(cauDienKhuyet)
    }

    private fun checkDapAnInGoiY(dapan: String, goiy: String): Boolean {
        var goiy = goiy.replace("\\W".toRegex(), " ")
        goiy = goiy.trim { it <= ' ' }.replace("\\s{2,}".toRegex(), " ")
        val dapAn = goiy.split(" ").toTypedArray()
        return dapAn.contains(dapan)
    }
}