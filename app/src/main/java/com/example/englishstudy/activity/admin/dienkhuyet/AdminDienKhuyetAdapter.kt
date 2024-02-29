package com.example.englishstudy.activity.admin.dienkhuyet

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.viewmodel.CauDienKhuyetViewModel

class AdminDienKhuyetAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private var list: ArrayList<CauDienKhuyet>
) : BaseAdapter() {
    private lateinit var dienKhuyetViewModel: CauDienKhuyetViewModel

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.row_dienkhuyet, null)
        val txtTenDienKhuyet = view.findViewById<TextView>(R.id.tvTenDienKhuyet)
        val imgEdit = view.findViewById<ImageView>(R.id.imgEditDK)
        val imgDelete = view.findViewById<ImageView>(R.id.imgDeleteDK)
        val dk = list[position]

        txtTenDienKhuyet.text = dk.noidung

        imgEdit.setOnClickListener {
            val intent = Intent(context, EditDienKhuyetActivity::class.java)
            intent.putExtra("ID_DK", dk.id)
            context.startActivity(intent)
        }

        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn chắc chắn muốn xóa câu điền khuyết này?")
            builder.setPositiveButton("Có") { _, _ ->
                val result = deleteDienKhuyet(dk)
                list.clear()
                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
                list = getDienKhuyet(dk.idBo)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("Không") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        return view
    }

    private fun deleteDienKhuyet(cauDienKhuyet: CauDienKhuyet): Boolean {
        dienKhuyetViewModel = ViewModelProvider(context as Activity).get(CauDienKhuyetViewModel::class.java)

        return try {
            dienKhuyetViewModel.deleteCauDienKhuyet(cauDienKhuyet)

            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getDienKhuyet(id: Int): ArrayList<CauDienKhuyet> {
        val listDK = ArrayList<CauDienKhuyet>()

        dienKhuyetViewModel = ViewModelProvider(context as Activity).get(CauDienKhuyetViewModel::class.java)
        dienKhuyetViewModel.getListCauDienKhuyetByIdBo(id).observe(lifecycleOwner) { dienKhuyet ->
            listDK.clear()
            listDK.addAll(dienKhuyet)
            notifyDataSetChanged()
        }

        return listDK
    }

    private fun ViewModelProvider(owner: Activity): ViewModelProvider {
        return ViewModelProvider(owner)
    }
}