package com.example.englishstudy.activity.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.englishstudy.R
import com.example.englishstudy.activity.taikhoan.LoginActivity

class LogoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        startActivity(Intent(activity, LoginActivity::class.java))
        return root
    }
}