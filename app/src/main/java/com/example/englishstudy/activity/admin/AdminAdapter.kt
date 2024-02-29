package com.example.englishstudy.activity.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.englishstudy.R

class AdminAdapter(
    private val context: Context,
    private val layout: Int,
    private val list: ArrayList<String>
) : BaseAdapter() {

    private class ViewHolder(val txtTenBo: TextView)

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
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder = ViewHolder(view.findViewById(R.id.tvTenBo))
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val itemList = list[position]
        holder.txtTenBo.text = itemList

        return view
    }
}