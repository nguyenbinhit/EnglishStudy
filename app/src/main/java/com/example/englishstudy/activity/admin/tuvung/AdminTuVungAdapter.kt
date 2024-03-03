package com.example.englishstudy.activity.admin.tuvung

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.lifecycle.ViewModelStoreOwner
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.viewmodel.TuVungViewMModel

class AdminTuVungAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private var list: ArrayList<TuVung>
) :
    BaseAdapter() {
    private lateinit var tuVungViewMModel: TuVungViewMModel

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
        val view = inflater.inflate(R.layout.row_tuvung, null)
        val txtTenTuVung = view.findViewById<TextView>(R.id.tvTenTuVung)
        val imgEdit = view.findViewById<ImageView>(R.id.imgEditTV)
        val imgDelete = view.findViewById<ImageView>(R.id.imgDeleteTV)
        val tv = list[position]
        txtTenTuVung.text = tv.dapAn
        imgEdit.setOnClickListener {
            val intent = Intent(context, EditTuVungActivity::class.java)
            intent.putExtra("ID_TV", tv.id)
            context.startActivity(intent)
        }
        imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Xác nhận xóa")
            builder.setMessage("Bạn chắc chắn muốn xóa từ vựng này?")
            builder.setPositiveButton("Có") { _: DialogInterface, _: Int ->
                val result = deleteTuVung(tv)
                list.clear()
                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
                list = getTuVung(tv.idBo)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("Không") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        return view
    }

    private fun deleteTuVung(tuVung: TuVung): Boolean {
        tuVungViewMModel = ViewModelProvider(this.context as ViewModelStoreOwner).get(TuVungViewMModel::class.java)

        return try {
            tuVungViewMModel.deleteTuVung(tuVung)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getTuVung(id: Int): ArrayList<TuVung> {
        val listTV = ArrayList<TuVung>()

        tuVungViewMModel = ViewModelProvider(context as ViewModelStoreOwner).get(TuVungViewMModel::class.java)

        tuVungViewMModel.getListTuVungByIdBo(id).observe(lifecycleOwner, Observer {
            listTV.clear()
            listTV.addAll(it)
            notifyDataSetChanged()
        })

        return listTV
    }

}