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
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.viewmodel.TuVungViewMModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

class EditTuVungActivity : AppCompatActivity() {
    private lateinit var imgBack: ImageView
    private lateinit var imgHinh: ImageView
    private lateinit var imgEdit: ImageView
    private lateinit var btnChonHinh: Button
    private lateinit var edtTuVung: EditText
    private lateinit var edtNghia: EditText
    private lateinit var edtAudio: EditText
    private lateinit var spnTuLoai: Spinner
    private lateinit var tuVungViewModel: TuVungViewMModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tuvung)
        imgBack = findViewById(R.id.imgBackEditTV)
        imgHinh = findViewById(R.id.imgHinhEditTV)
        imgEdit = findViewById(R.id.imgEditTV)
        btnChonHinh = findViewById(R.id.btnChonHinhEditTV)
        edtTuVung = findViewById(R.id.edtTuVungEditTV)
        edtNghia = findViewById(R.id.edtNghiaEditTV)
        edtAudio = findViewById(R.id.edtAudioEditTV)
        spnTuLoai = findViewById(R.id.spnLoaiTuEditTV)

        val listTuLoai = arrayListOf("Danh từ", "Động từ", "Tính từ", "Trạng từ", "Giới từ")
        val tuLoaiAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listTuLoai)
        spnTuLoai.adapter = tuLoaiAdapter

        tuVungViewModel = ViewModelProvider(this).get(TuVungViewMModel::class.java)
        val idTV = intent.getIntExtra("ID_TV", -1)
        lifecycleScope.launch (Dispatchers.IO) {
            val tuVung = tuVungViewModel.getListTuVungById(idTV)
            withContext(Dispatchers.Main) {
                // Update your UI with the tuVung data here
                edtTuVung.setText(tuVung.dapAn)
                edtNghia.setText(tuVung.dichNghia)
                edtAudio.setText(tuVung.audio)
                when (tuVung.loaiTu) {
                    "Danh từ" -> spnTuLoai.setSelection(0)
                    "Động từ" -> spnTuLoai.setSelection(1)
                    "Tính từ" -> spnTuLoai.setSelection(2)
                    "Trạng từ" -> spnTuLoai.setSelection(3)
                    "Giới từ" -> spnTuLoai.setSelection(4)
                }
                val bmp = tuVung.anh?.let { BitmapFactory.decodeByteArray(tuVung.anh, 0, it.size) }
                imgHinh.setImageBitmap(bmp)

                imgBack.setOnClickListener {
                    val intent = Intent(this@EditTuVungActivity, AdminTuVungActivity::class.java)
                    intent.putExtra("idBoTuVung", tuVung.idBo)
                    startActivity(intent)
                }
                imgEdit.setOnClickListener {
                    val dapan = edtTuVung.text.toString()
                    val nghia = edtNghia.text.toString()
                    val audio = edtAudio.text.toString()
                    val loaitu = spnTuLoai.selectedItem.toString()
                    val anh = getByteArrayFromImageView(imgHinh)
                    if (dapan == "" || nghia == "" || audio == "" || loaitu == "") {
                        Toast.makeText(this@EditTuVungActivity, "Chưa điền đầy thông tin", Toast.LENGTH_SHORT).show()
                    } else {
                        val result =
                            updateTuVung(tuVung.id, tuVung.idBo, dapan, nghia, loaitu, audio, anh)
                        if (result) {
                            Toast.makeText(this@EditTuVungActivity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@EditTuVungActivity, AdminTuVungActivity::class.java)
                            intent.putExtra("idBoTuVung", tuVung.idBo)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@EditTuVungActivity, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        btnChonHinh.setOnClickListener {
            choosePhoto()
        }
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    private fun updateTuVung(
        idtu: Int,
        idbo: Int,
        dapan: String,
        nghia: String,
        loaitu: String,
        audio: String,
        anh: ByteArray
    ): Boolean {
        tuVungViewModel = ViewModelProvider(this).get(TuVungViewMModel::class.java)

        val tuVung = TuVung(idbo, dapan, nghia, loaitu, audio, anh).apply { id = idtu }

        try {
            tuVungViewModel.updateTuVung(tuVung)
            return true
        } catch (e: Exception) {
            return false
        }
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
                    val isStream: InputStream? =
                        imageUri?.let { contentResolver.openInputStream(it) }
                    val bmp = BitmapFactory.decodeStream(isStream)
                    imgHinh.setImageBitmap(bmp)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val REQUEST_CHOOSE_PHOTO = 321
    }
}