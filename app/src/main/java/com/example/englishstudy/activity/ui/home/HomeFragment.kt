package com.example.englishstudy.activity.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.activity.dienkhuyet.DienKhuyetActivity

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cardViewHocTuVung: CardView
    private lateinit var cardViewDienKhuyet: CardView
    private lateinit var cardViewTracNghiem: CardView
    private lateinit var cardViewSapXepCau: CardView
    private lateinit var cardViewLuyenNghe: CardView
    private lateinit var cardViewXepHang: CardView
    private lateinit var imgview: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        cardViewHocTuVung = root.findViewById(R.id.cardViewHocTuVung)
        cardViewDienKhuyet = root.findViewById(R.id.cardViewDienKhuyet)
        cardViewTracNghiem = root.findViewById(R.id.cardViewTracNghiem)
        cardViewSapXepCau = root.findViewById(R.id.cardViewSapXepCau)
        cardViewLuyenNghe = root.findViewById(R.id.cardViewLuyenNghe)
        cardViewXepHang = root.findViewById(R.id.cardViewXepHang)

        setCardViewListeners()

        return root
    }

    private fun setCardViewListeners() {
//        cardViewHocTuVung.setOnClickListener {
//            val intent = Intent(activity, HocTuVungActivity::class.java)
//            startActivity(intent)
//        }

        cardViewDienKhuyet.setOnClickListener {
            val intent = Intent(activity, DienKhuyetActivity::class.java)
            startActivity(intent)
        }

//        cardViewTracNghiem.setOnClickListener {
//            val intent = Intent(activity, TracNghiemActivity::class.java)
//            startActivity(intent)
//        }
//
//        cardViewSapXepCau.setOnClickListener {
//            val intent = Intent(activity, SapXepCauActivity::class.java)
//            startActivity(intent)
//        }
//
//        cardViewLuyenNghe.setOnClickListener {
//            val intent = Intent(activity, LuyenNgheActivity::class.java)
//            startActivity(intent)
//        }
//
//        cardViewXepHang.setOnClickListener {
//            val intent = Intent(activity, RankingActivity::class.java)
//            startActivity(intent)
//        }
    }
}