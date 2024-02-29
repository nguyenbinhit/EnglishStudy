package com.example.englishstudy.activity.admin.luyennghe

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

class EditLuyenNgheActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgHinh: ImageView
    private lateinit var imgEdit: ImageView
    private lateinit var btnChonHinh: Button
    private lateinit var edtAudio: EditText
    private lateinit var spnDapAnTrue: Spinner
    private val REQUEST_CHOOSE_PHOTO = 321
    private lateinit var luyenNgheViewModel: CauLuyenNgheViewModel
    private lateinit var cauLuyenNghe: CauLuyenNghe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_luyennghe)
        imgBack = findViewById(R.id.imgBackEditLN)
        imgHinh = findViewById(R.id.imgHinhEditLN)
        imgEdit = findViewById(R.id.imgEditLN)
        btnChonHinh = findViewById(R.id.btnChonHinhEditLN)
        edtAudio = findViewById(R.id.edtAudioEditLN)
        spnDapAnTrue = findViewById(R.id.spnDapAnTrueEditLN)

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

        val idLN = intent.getIntExtra("ID_LN", -1)

        val luyenNghe = getLuyenNgheByID(idLN)

        edtAudio.setText(luyenNghe.audio)
        when (luyenNghe.dapAnDung) {
            "1" -> spnDapAnTrue.setSelection(0)
            "2" -> spnDapAnTrue.setSelection(1)
            "3" -> spnDapAnTrue.setSelection(2)
            "4" -> spnDapAnTrue.setSelection(3)
        }

        val bmp = BitmapFactory.decodeByteArray(luyenNghe.hinhAnh, 0, luyenNghe.hinhAnh.size)
        imgHinh.setImageBitmap(bmp)

        imgBack.setOnClickListener {
            val intent = Intent(this, AdminLuyenNgheActivity::class.java)
            intent.putExtra("idBoLuyenNghe", luyenNghe.idBo)
            startActivity(intent)
        }

        imgEdit.setOnClickListener {
            val audio = edtAudio.text.toString()
            val dapanDung = spnDapAnTrue.selectedItem.toString()
            var dapanTrue = ""
            val hinhAnh = getByteArrayFromImageView(imgHinh)
            if (audio == "") {
                Toast.makeText(this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                when (dapanDung) {
                    "A" -> dapanTrue = "1"
                    "B" -> dapanTrue = "2"
                    "C" -> dapanTrue = "3"
                    "D" -> dapanTrue = "4"
                }

                val result = updateLuyenNghe(
                    luyenNghe.id,
                    luyenNghe.idBo,
                    "A",
                    "B",
                    "C",
                    "D",
                    dapanTrue,
                    hinhAnh,
                    audio
                )

                if (result) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminLuyenNgheActivity::class.java)
                    intent.putExtra("idBoLuyenNghe", luyenNghe.idBo)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnChonHinh.setOnClickListener {
            choosePhoto()
        }
    }

    private fun getLuyenNgheByID(id: Int): CauLuyenNghe {
        luyenNgheViewModel.getCauLuyenNgheById(
            id,
            object : CauLuyenNgheViewModel.CauLuyenNgheCallback {
                override fun onCauLuyenNgheLoaded(luyenNghe: CauLuyenNghe) {
                    cauLuyenNghe = luyenNghe
                }
            })

        return cauLuyenNghe
    }

    private fun updateLuyenNghe(
        idbai: Int,
        idbo: Int,
        dapanA: String,
        dapanB: String,
        dapanC: String,
        dapanD: String,
        dapanTrue: String,
        hinhAnh: ByteArray,
        audio: String
    ): Boolean {
        try {
            val cauLuyenNghe = CauLuyenNghe(
                idBo = idbo,
                dapAnA = dapanA,
                dapAnB = dapanB,
                dapAnC = dapanC,
                dapAnD = dapanD,
                dapAnDung = dapanTrue,
                hinhAnh = hinhAnh,
                audio = audio
            ).apply { id = idbai }

            luyenNgheViewModel.updateCauLuyenNghe(cauLuyenNghe)

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

    private fun getByteArrayFromImageView(img: ImageView): ByteArray {
        val drawable = img.drawable as BitmapDrawable
        val bmp = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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