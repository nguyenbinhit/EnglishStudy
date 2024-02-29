package com.example.englishstudy.activity.sapxepcau

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.englishstudy.MainActivity
import com.example.englishstudy.R

class FinishSXCActivity : AppCompatActivity() {
    private lateinit var txtcongrats: TextView
    private lateinit var txtfinalqtrue: TextView
    private lateinit var txtfinaltext: TextView
    private lateinit var txtfinalScore: TextView
    private lateinit var btnReturn: Button
    private lateinit var imgtrophy: ImageView
    private var score = 0
    private var questiontrue = 0
    private var qcount = 0
    private lateinit var smalltobig: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_hoctap)

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig)
        val intent = intent
        score = intent.getIntExtra("score", 0)
        questiontrue = intent.getIntExtra("questiontrue", 0)
        qcount = intent.getIntExtra("qcount", 0)

        anhXa()

        when {
            questiontrue == 4 -> {
                txtfinalqtrue.text = "$questiontrue / $qcount"
                txtcongrats.text = "Your final result: "
                txtfinaltext.text = "Almost there!!"
                txtfinalScore.text = " $score"
            }

            questiontrue < 4 -> {
                txtfinalqtrue.text = "$questiontrue / $qcount"
                txtcongrats.text = "Your final result: "
                txtfinaltext.text = "Good luck next time !!"
                txtfinalScore.text = " $score"
            }
        }
        btnReturn.setOnClickListener {
            startActivity(Intent(this@FinishSXCActivity, MainActivity::class.java))
        }
    }

    private fun anhXa() {
        txtfinalScore = findViewById(R.id.tvPoints)
        txtcongrats = findViewById(R.id.txtcongrats)
        txtfinalqtrue = findViewById(R.id.txtquestiontrue)
        txtfinaltext = findViewById(R.id.txtfinaltext)
        btnReturn = findViewById(R.id.btnReturn)
        imgtrophy = findViewById(R.id.imgtrophy)
        imgtrophy.startAnimation(smalltobig)
    }
}