package com.example.englishstudy.activity.admin.luyennghe

import android.annotation.SuppressLint
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
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauLuyenNghe
import com.example.englishstudy.viewmodel.CauLuyenNgheViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

class AddLuyenNgheActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgHinh: ImageView
    private lateinit var imgAdd: ImageView
    private lateinit var btnChonHinh: Button
    private lateinit var edtAudio: EditText
    private lateinit var spnDapAnTrue: Spinner
    private val REQUEST_CHOOSE_PHOTO = 321
    private var idBLN = 0
    private lateinit var luyenNgheViewModel: CauLuyenNgheViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_luyennghe)
        imgBack = findViewById(R.id.imgBackAddLN)
        imgHinh = findViewById(R.id.imgHinhAddLN)
        imgAdd = findViewById(R.id.imgAddLN)
        btnChonHinh = findViewById(R.id.btnChonHinhAddLN)
        edtAudio = findViewById(R.id.edtAudioAddLN)
        spnDapAnTrue = findViewById(R.id.spnDapAnTrueAddLN)

        val listDapAn = ArrayList<String>()
        listDapAn.add("A")
        listDapAn.add("B")
        listDapAn.add("C")
        listDapAn.add("D")

        val dapAnAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, listDapAn
        )

        spnDapAnTrue.adapter = dapAnAdapter
        idBLN = intent.getIntExtra("idBoLuyenNghe", -1)

        btnChonHinh.setOnClickListener {
            choosePhoto()
        }

        imgBack.setOnClickListener {
            val intent = Intent(this, AdminLuyenNgheActivity::class.java)
            intent.putExtra("idBoLuyenNghe", idBLN)
            startActivity(intent)
        }

        imgAdd.setOnClickListener {
            val audio = edtAudio.text.toString()
            val dapanDung = spnDapAnTrue.selectedItem.toString()
            var dapanTrue = ""

            if (audio == "" || imgHinh.drawable == null) {
                Toast.makeText(this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                val hinhAnh = getByteArrayFromImageView(imgHinh)
                when (dapanDung) {
                    "A" -> dapanTrue = "1"
                    "B" -> dapanTrue = "2"
                    "C" -> dapanTrue = "3"
                    "D" -> dapanTrue = "4"
                }
                val result = addLuyenNghe(idBLN, "A", "B", "C", "D", dapanTrue, hinhAnh, audio)
                if (result) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminLuyenNgheActivity::class.java)
                    intent.putExtra("idBoLuyenNghe", idBLN)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addLuyenNghe(
        idbo: Int,
        dapanA: String,
        dapanB: String,
        dapanC: String,
        dapanD: String,
        dapanTrue: String,
        hinhAnh: ByteArray,
        audio: String
    ): Boolean {
        val cauLuyenNghe =
            CauLuyenNghe(idbo, dapanA, dapanB, dapanC, dapanD, dapanTrue, hinhAnh, audio)

        return try {
            luyenNgheViewModel.createCauLuyenNghe(cauLuyenNghe)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    private fun getByteArrayFromImageView(img: ImageView): ByteArray {
        val drawable = img.drawable as BitmapDrawable
        val bmp = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    val imageUri = data?.data
                    val `is`: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                    val bmp = BitmapFactory.decodeStream(`is`)
                    imgHinh.setImageBitmap(bmp)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }
}