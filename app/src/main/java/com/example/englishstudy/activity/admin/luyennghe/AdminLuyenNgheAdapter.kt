package com.example.englishstudy.activity.admin.luyennghe

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
import com.example.englishstudy.model.entity.CauLuyenNghe
import com.example.englishstudy.viewmodel.CauLuyenNgheViewModel

class AdminLuyenNgheAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private var list: ArrayList<CauLuyenNghe>
) : BaseAdapter() {
    private lateinit var luyenNgheViewModel: CauLuyenNgheViewModel

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
        val view = inflater.inflate(R.layout.row_luyennghe, null)
        val txtTenLuyenNghe = view.findViewById<TextView>(R.id.tvTenLuyenNghe)
        val imgEdit = view.findViewById<ImageView>(R.id.imgEditLN)
        val imgDelete = view.findViewById<ImageView>(R.id.imgDeleteLN)

        val ln = list[position]
        txtTenLuyenNghe.text = "Bài " + ln.id + ""

        imgEdit.setOnClickListener {
            val intent = Intent(context, EditLuyenNgheActivity::class.java)
            intent.putExtra("ID_LN", ln.id)
            context.startActivity(intent)
        }

        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn chắc chắn muốn xóa bài nghe này?")
            builder.setPositiveButton("Có") { _, _ ->
                val result = deleteLuyenNghe(ln)
                list.clear()
                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
                list = getLuyenNghe(ln.idBo)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("Không") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        return view
    }

    private fun deleteLuyenNghe(cauLuyenNghe: CauLuyenNghe): Boolean {
        luyenNgheViewModel = ViewModelProvider(context as Activity).get(CauLuyenNgheViewModel::class.java)

        return try {
            luyenNgheViewModel.deleteCauLuyenNghe(cauLuyenNghe)

            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getLuyenNghe(id: Int): ArrayList<CauLuyenNghe> {
        val listLN = ArrayList<CauLuyenNghe>()

        luyenNgheViewModel = ViewModelProvider(context as Activity).get(CauLuyenNgheViewModel::class.java)
        luyenNgheViewModel.getListCauLuyenNgheByIdBo(id).observe(lifecycleOwner, Observer { luyenNghes ->
            listLN.clear()
            listLN.addAll(luyenNghes)
            notifyDataSetChanged()
        })

        return listLN
    }

    private fun ViewModelProvider(owner: Activity): ViewModelProvider {
        return ViewModelProvider(owner)
    }
}