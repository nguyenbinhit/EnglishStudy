package com.example.englishstudy.activity.admin.bohoctap

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.BoHocTap
import com.example.englishstudy.viewmodel.BoHocTapViewModel

class AdminBoHocTapAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val list: ArrayList<BoHocTap>
) :
    BaseAdapter() {
    private lateinit var boHocTapViewModel: BoHocTapViewModel

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.row_bohoctap, null)
        val txtTenBo = view.findViewById<TextView>(R.id.tvTenBo)
        val imgEdit = view.findViewById<ImageView>(R.id.imgEditBHT)
        val imgDelete = view.findViewById<ImageView>(R.id.imgDeleteBHT)
        val bht = list[position]
        txtTenBo.text = bht.tenBo + ""

        imgEdit.setOnClickListener {
            val intent = Intent(context, EditBoHocTapActivity::class.java)
            intent.putExtra("ID_BHT", bht.id)
            context.startActivity(intent)
        }

        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn chắc chắn muốn xóa bộ học tập này?")
            builder.setPositiveButton("Có") { dialog, which ->
                val result = deleteBoHocTap(bht)

                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }

                getBoHocTap()
            }
            builder.setNegativeButton("Không") { dialog, which ->

            }
            val dialog = builder.create()
            dialog.show()
        }
        return view
    }

    private fun deleteBoHocTap(boHocTap: BoHocTap): Boolean {
        boHocTapViewModel =
            ViewModelProvider(lifecycleOwner).get(BoHocTapViewModel::class.java)

        return try {
            boHocTapViewModel.deleteBoHocTap(boHocTap)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun ViewModelProvider(owner: LifecycleOwner): ViewModelProvider {
        return ViewModelProvider(owner)
    }

    private fun getBoHocTap() {
        boHocTapViewModel =
            ViewModelProvider(lifecycleOwner).get(BoHocTapViewModel::class.java)

        boHocTapViewModel.getListBoHocTap().observe(lifecycleOwner, Observer { boHocTaps ->
            list.clear()
            list.addAll(boHocTaps)
            notifyDataSetChanged()
        })
    }
}