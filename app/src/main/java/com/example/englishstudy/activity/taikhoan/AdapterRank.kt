package com.example.englishstudy.activity.taikhoan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.User

class AdapterRank(private val context: Context, private val list: ArrayList<User>) :
    RecyclerView.Adapter<AdapterRank.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.row_xephang, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userRanking = list[position]
        holder.email.text = userRanking.email
        holder.hoTen.text = userRanking.name
        holder.point.text = userRanking.point.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var email: TextView = itemView.findViewById(R.id.tvemailrank)
        var hoTen: TextView = itemView.findViewById(R.id.tvhotenrank)
        var point: TextView = itemView.findViewById(R.id.tvpointrank)
    }
}