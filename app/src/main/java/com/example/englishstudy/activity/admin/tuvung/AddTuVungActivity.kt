package com.example.englishstudy.activity.admin.tuvung

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.viewmodel.TuVungViewMModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

class AddTuVungActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgHinh: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var btnChonHinh: Button
    private lateinit var edtTuVung: EditText
    private lateinit var edtNghia: EditText
    private lateinit var edtAudio: EditText
    private lateinit var spnTuLoai: Spinner
    private val REQUEST_CHOOSE_PHOTO = 321
    private var idBTV = 0
    private var listTuLoai: ArrayList<String> = ArrayList()
    private lateinit var tuVungViewModel: TuVungViewMModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tuvung)

        imgBack = findViewById(R.id.imgBackAddTV)
        imgHinh = findViewById(R.id.imgHinhAddTV)
        imgAdd = findViewById(R.id.imgAddTV)
        btnChonHinh = findViewById(R.id.btnChonHinhAddTV)
        edtTuVung = findViewById(R.id.edtTuVungAddTV)
        edtNghia = findViewById(R.id.edtNghiaAddTV)
        edtAudio = findViewById(R.id.edtAudioAddTV)
        spnTuLoai = findViewById(R.id.spnLoaiTuAddTV)

        listTuLoai.add("Danh từ")
        listTuLoai.add("Động từ")
        listTuLoai.add("Tính từ")
        listTuLoai.add("Trạng từ")
        listTuLoai.add("Giới từ")

        val tuLoaiAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, listTuLoai
        )
        spnTuLoai.adapter = tuLoaiAdapter
        idBTV = intent.getIntExtra("idBoTuVung", -1)
        btnChonHinh.setOnClickListener {
            choosePhoto()
        }

        imgBack.setOnClickListener {
            val intent = Intent(this, AdminTuVungActivity::class.java)
            intent.putExtra("idBoTuVung", idBTV)
            finishAffinity()
            startActivity(intent)
        }

        imgAdd.setOnClickListener {
            val dapan = edtTuVung.text.toString().trim()
            val nghia = edtNghia.text.toString().trim()
            val audio = edtAudio.text.toString().trim()

            val indexValue = spnTuLoai.selectedItemPosition
            val loaitu = listTuLoai[indexValue]

            if (dapan == "" || nghia == "" || audio == "" || loaitu == "" || imgHinh.drawable == null) {
                Toast.makeText(this, "Chưa điền đầy thông tin", Toast.LENGTH_SHORT).show()
            } else {
                val anh = getByteArrayFromImageView()

                var result = false
                if (anh != null && anh.isNotEmpty()) {
                    result = addTuVung(idBTV, dapan, nghia, loaitu, audio, anh)
                }

                if (result) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminTuVungActivity::class.java)
                    intent.putExtra("idBoTuVung", idBTV)
                    finishAffinity()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addTuVung(
        idbo: Int,
        dapan: String,
        nghia: String,
        loaitu: String,
        audio: String,
        anh: ByteArray
    ): Boolean {
        val tuVung = TuVung(idbo, dapan, nghia, loaitu, audio, anh)

        try {
            tuVungViewModel = ViewModelProvider(this).get(TuVungViewMModel::class.java)
            tuVungViewModel.createTuVung(tuVung)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    private fun getByteArrayFromImageView(): ByteArray {
        val drawable = imgHinh.drawable as BitmapDrawable
        val bmp = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    val imageUri = data?.data
                    val `is`: InputStream? = contentResolver.openInputStream(imageUri!!)
                    val bmp = BitmapFactory.decodeStream(`is`)
                    imgHinh.setImageBitmap(bmp)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }
}