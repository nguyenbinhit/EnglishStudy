package com.example.englishstudy.activity.admin.sapxepcau

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
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.viewmodel.CauSapXepViewModel

class AdminSapXepCauAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private var list: ArrayList<CauSapXep>
) :
    BaseAdapter() {
    private lateinit var sapXepViewModel: CauSapXepViewModel

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
        val view = inflater.inflate(R.layout.row_sapxepcau, null)
        val txtTenSapXepCau = view.findViewById<TextView>(R.id.tvTenSapXepCau)
        val imgEdit = view.findViewById<ImageView>(R.id.imgEditSXC)
        val imgDelete = view.findViewById<ImageView>(R.id.imgDeleteSXC)
        val sxc = list[position]

        txtTenSapXepCau.text = sxc.dapAn + ""

        imgEdit.setOnClickListener {
            val intent = Intent(context, EditSapXepCauActivity::class.java)
            intent.putExtra("ID_SXC", sxc.id)
            context.startActivity(intent)
        }

        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn chắc chắn muốn xóa câu sắp xếp này?")
            builder.setPositiveButton("Có") { _, _ ->
                val result = deleteSapXepCau(sxc)
                list.clear()
                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
                list = getSapXepCau(sxc.idBo)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("Không") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }
        return view
    }

    private fun deleteSapXepCau(cauSapXep: CauSapXep): Boolean {
        sapXepViewModel = ViewModelProvider(context as Activity).get(CauSapXepViewModel::class.java)

        return try {
            sapXepViewModel.deleteCauSapXep(cauSapXep)

            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getSapXepCau(id: Int): ArrayList<CauSapXep> {
        val listSXC = ArrayList<CauSapXep>()

        sapXepViewModel = ViewModelProvider(context as Activity).get(CauSapXepViewModel::class.java)
        sapXepViewModel.getListCauSapXepByIdBo(id).observe(lifecycleOwner, Observer {
            listSXC.clear()
            listSXC.addAll(it)
            notifyDataSetChanged()
        })

        return listSXC
    }

    private fun ViewModelProvider(owner: Activity): ViewModelProvider {
        return ViewModelProvider(owner)
    }
}