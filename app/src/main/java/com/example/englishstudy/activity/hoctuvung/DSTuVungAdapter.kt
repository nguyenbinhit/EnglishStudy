package com.example.englishstudy.activity.hoctuvung

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.TuVung

class DSTuVungAdapter(
    private val context: Context,
    private val layout: Int,
    private var dstuvungs: List<TuVung>
) : BaseAdapter() {

    private class ViewHolder {
        lateinit var imgHinh: ImageView
        lateinit var twDichNghia: TextView
        lateinit var twTuVung: TextView
    }

    override fun getCount(): Int {
        return dstuvungs.size
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
            holder.imgHinh = view.findViewById(R.id.imgHinh)
            holder.twDichNghia = view.findViewById(R.id.twDichNghia)
            holder.twTuVung = view.findViewById(R.id.twTuVung)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val tuVung = dstuvungs[position]
        holder.twDichNghia.text = tuVung.dichNghia
        holder.twTuVung.text = "${tuVung.dapAn}(${tuVung.loaiTu}):"
        val img = tuVung.anh?.let { BitmapFactory.decodeByteArray(tuVung.anh, 0, it.size) }
        holder.imgHinh.setImageBitmap(img)

        return view
    }
}