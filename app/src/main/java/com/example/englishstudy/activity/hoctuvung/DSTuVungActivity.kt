package com.example.englishstudy.activity.hoctuvung

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.viewmodel.TuVungViewMModel

class DSTuVungActivity : AppCompatActivity() {
    private lateinit var imgback: ImageView
    private lateinit var dstuvungs: GridView
    private lateinit var Ontap: Button
    private lateinit var DStuvung: ArrayList<TuVung>
    private lateinit var adapter: DSTuVungAdapter
    private var idbo = 0
    private lateinit var tuVungViewMModel: TuVungViewMModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ds_tuvung)

        val intent = intent
        idbo = intent.getIntExtra("idbo", 0)
        dstuvungs = findViewById(R.id.lgvTuVung)
        Ontap = findViewById(R.id.btnOnTap)
        imgback = findViewById(R.id.imgVBackDSTV)
        DStuvung = ArrayList()

        addArrayTV()

//        if (DStuvung.size <= 0) {
//            Toast.makeText(
//                this,
//                "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!",
//                Toast.LENGTH_LONG
//            ).show()
//            val error = Intent(this, HocTuVungActivity::class.java)
//            finish()
//            startActivity(error)
//        } else {
            adapter = DSTuVungAdapter(this, R.layout.row_dstuvung, DStuvung)
            dstuvungs.adapter = adapter
            adapter.notifyDataSetChanged()

            imgback.setOnClickListener {
                val intent = Intent(this, HocTuVungActivity::class.java)
                startActivity(intent)
            }

            Ontap.setOnClickListener {
                val ontap = Intent(this, WordMattersActivity::class.java)
                ontap.putExtra("idbo", idbo)
                startActivity(ontap)
            }
//        }
    }

    private fun addArrayTV() {
        tuVungViewMModel = ViewModelProvider(this).get(TuVungViewMModel::class.java)
        tuVungViewMModel.getListTuVungByIdBo(idbo).observe(this, Observer { tuVungs ->
            DStuvung.clear()
            DStuvung.addAll(tuVungs)
            adapter.notifyDataSetChanged()
        })
    }
}