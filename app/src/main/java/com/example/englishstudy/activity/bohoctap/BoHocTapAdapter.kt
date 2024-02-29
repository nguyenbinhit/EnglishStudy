package com.example.englishstudy.activity.bohoctap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.BoHocTap

class BoHocTapAdapter(
    private val context: Context,
    private val layout: Int,
    private val boHocTapList: List<BoHocTap>
) : BaseAdapter() {

    private class ViewHolder {
        lateinit var txtTenBo: TextView
    }

    override fun getCount(): Int {
        return boHocTapList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            holder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder.txtTenBo = view.findViewById(R.id.tvTenBo)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val boHT = boHocTapList[position]
        holder.txtTenBo.text = boHT.tenBo
        return view
    }
}