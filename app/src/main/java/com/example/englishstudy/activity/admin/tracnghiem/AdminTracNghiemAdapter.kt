package com.example.englishstudy.activity.admin.tracnghiem

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
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.viewmodel.CauTracNghiemViewModel

class AdminTracNghiemAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private var list: ArrayList<CauTracNghiem>
) : BaseAdapter() {
    private lateinit var cauTracNghiemViewModel: CauTracNghiemViewModel

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
        val view = inflater.inflate(R.layout.row_tracnghiem, null)
        val txtTenTracNghiem = view.findViewById<TextView>(R.id.tvTenTracNghiem)
        val imgEdit = view.findViewById<ImageView>(R.id.imgEditTN)
        val imgDelete = view.findViewById<ImageView>(R.id.imgDeleteTN)
        val tn = list[position]

        txtTenTracNghiem.text = tn.noiDung + ""

        imgEdit.setOnClickListener {
            val intent = Intent(context, EditTracNghiemActivity::class.java)
            intent.putExtra("ID_TN", tn.id)
            context.startActivity(intent)
        }

        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn chắc chắn muốn xóa câu trắc nghiệm này?")
            builder.setPositiveButton("Có") { _, _ ->
                val result = deleteTracNghiem(tn)
                list.clear()
                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
                list = getTracNghiem(tn.idBo)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("Không") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        return view
    }

    private fun deleteTracNghiem(cauTracNghiem: CauTracNghiem): Boolean {
        cauTracNghiemViewModel =
            ViewModelProvider(context as Activity).get(CauTracNghiemViewModel::class.java)

        return try {
            cauTracNghiemViewModel.deleteCauTracNghiem(cauTracNghiem)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getTracNghiem(id: Int): ArrayList<CauTracNghiem> {
        val listTN = ArrayList<CauTracNghiem>()

        cauTracNghiemViewModel =
            ViewModelProvider(context as Activity).get(CauTracNghiemViewModel::class.java)

        cauTracNghiemViewModel.getListCauTracNghiemByIdBo(id).observe(lifecycleOwner, Observer {
            listTN.clear()
            listTN.addAll(it)
            notifyDataSetChanged()
        })

        return listTN
    }

    private fun ViewModelProvider(owner: Activity): ViewModelProvider {
        return ViewModelProvider(owner)
    }
}