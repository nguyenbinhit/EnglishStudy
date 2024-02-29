package com.example.englishstudy.activity.luyennghe

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauLuyenNghe
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.CauLuyenNgheViewModel
import com.example.englishstudy.viewmodel.UserViewModel

class ListeningActivity : AppCompatActivity() {
    private lateinit var txtscore: TextView
    private lateinit var txtquestcount: TextView
    private lateinit var rdgchoices: RadioGroup
    private lateinit var btnop1: RadioButton
    private lateinit var btnop2: RadioButton
    private lateinit var btnop3: RadioButton
    private lateinit var btnop4: RadioButton
    private lateinit var btnconfirm: Button
    private lateinit var btnquit: Button
    private lateinit var imHA: ImageView
    private lateinit var cauLuyenNghes: ArrayList<CauLuyenNghe>
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var ImgBT: ImageButton
    private lateinit var user: User
    private var URL =
        "https://github.com/nguyenbinhit/Android/blob/main/LuyenNghe_Hey%20Mama%20-%20David%20Guetta%20feat_%20Nicki%20Mina.mp3"
    private var questioncurrent = 0
    private var questiontrue = 0
    private var answer = 0
    private var score = 0
    private var idbo = 0
    private lateinit var userViewModel: UserViewModel
    private lateinit var cauLuyenNgheViewModel: CauLuyenNgheViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_listening)

        anhXa()
        getUser()

        val intent = intent
        idbo = intent.getIntExtra("Bo", 0)
        cauLuyenNghes = ArrayList()

        addArrayCLN()

        if (cauLuyenNghes.size <= 0) {
            Toast.makeText(
                this,
                "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!",
                Toast.LENGTH_LONG
            ).show()
            val error = Intent(this, LuyenNgheActivity::class.java)
            finish()
            startActivity(error)
        } else {
            showNextQuestion(questioncurrent, cauLuyenNghes)

            mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            mediaPlayer.setOnPreparedListener { mp -> mp.start() }
            val countDownTimer = object : CountDownTimer(3000, 300) {
                override fun onTick(millisUntilFinished: Long) {
                    showAnswer()
                }

                override fun onFinish() {
                    btnconfirm.isEnabled = true
                    showNextQuestion(questioncurrent, cauLuyenNghes)
                }
            }
            btnconfirm.setOnClickListener {
                checkAns()
                questioncurrent++
                countDownTimer.start()
            }

            ImgBT.setOnClickListener {
                MediaPlayerUtils.playURLMedia(this, mediaPlayer, URL)
            }

            btnquit.setOnClickListener {
                finish()
                doStop()
                val intent = Intent(this, LuyenNgheActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun addArrayCLN() {
        cauLuyenNgheViewModel = ViewModelProvider(this).get(CauLuyenNgheViewModel::class.java)
        cauLuyenNgheViewModel.getListCauLuyenNgheByIdBo(idbo)
            .observe(this, Observer { cauLuyenNgheList ->
                cauLuyenNghes.clear()
                cauLuyenNghes.addAll(cauLuyenNgheList)
            })
    }

    private fun anhXa() {
        txtscore = findViewById(R.id.txtscoreLN)
        txtquestcount = findViewById(R.id.txtquestcountLN)
        rdgchoices = findViewById(R.id.radiochoices)
        btnop1 = findViewById(R.id.op1)
        btnop2 = findViewById(R.id.op2)
        btnop3 = findViewById(R.id.op3)
        btnop4 = findViewById(R.id.op4)
        btnconfirm = findViewById(R.id.btnconfirmLN)
        btnquit = findViewById(R.id.btnQuitLN)
        imHA = findViewById(R.id.imgHinh)
        ImgBT = findViewById(R.id.ImgBT)
    }

    private fun getCurrUserId(): Int {
        // Retrieve the id of the current user from shared preferences
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        return sharedPref.getInt("currentUserId", 0);
    }

    private fun getUser() {
        val currentUserId = getCurrUserId()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel.getUser(currentUserId).observe(this, Observer { user ->
            // Update your UI with the user data here
            val idUser = user.id
            val hoTen = user.name
            val point = user.point
            val email = user.email
            val role = user.role

            this.user = User(idUser.toString(), hoTen, email, point, role)
        })
    }

    private fun showNextQuestion(pos: Int, cauLuyenNghes: ArrayList<CauLuyenNghe>) {
        if (pos > 0) doStop()
        txtquestcount.text = "Question: " + (questioncurrent + 1) + "/" + cauLuyenNghes.size
        rdgchoices.clearCheck()
        btnop1.background = this.resources.getDrawable(R.drawable.bgbtn)
        btnop2.background = this.resources.getDrawable(R.drawable.bgbtn)
        btnop3.background = this.resources.getDrawable(R.drawable.bgbtn)
        btnop4.background = this.resources.getDrawable(R.drawable.bgbtn)
        if (pos == cauLuyenNghes.size) {
            userViewModel.updatePointUser(user.id, score)

            val intent = Intent(this, FinishQuizLSActivity::class.java)
            intent.putExtra("score", score)
            intent.putExtra("questiontrue", questiontrue)
            intent.putExtra("qcount", pos)
            startActivity(intent)
        } else {
            val anh = cauLuyenNghes[pos].hinhAnh
            val img = BitmapFactory.decodeByteArray(anh, 0, anh.size)
            imHA.setImageBitmap(img)

            val URLaudio = cauLuyenNghes[pos].audio
            URL = URLaudio

            answer = Integer.valueOf(cauLuyenNghes[pos].dapAnDung)
            btnop1.text = cauLuyenNghes[pos].dapAnA
            btnop2.text = cauLuyenNghes[pos].dapAnB
            btnop3.text = cauLuyenNghes[pos].dapAnC
            btnop4.text = cauLuyenNghes[pos].dapAnD
        }
    }

    private fun checkAns() {
        btnconfirm.isEnabled = false
        if (btnop1.isChecked) {
            if (1 == answer) {
                score += 5
                questiontrue++
            }
        }

        if (btnop2.isChecked) {
            if (2 == answer) {
                score += 5
                questiontrue++
            }
        }

        if (btnop3.isChecked) {
            if (3 == answer) {
                score += 5
                questiontrue++
            }
        }

        if (btnop4.isChecked) {
            if (4 == answer) {
                score += 5
                questiontrue++
            }
        }

        txtscore.text = "Score: $score"
    }

    private fun showAnswer() {
        if (1 == answer) {
            btnop1.background = this.resources.getDrawable(R.drawable.button_2)
            btnop2.background = this.resources.getDrawable(R.drawable.button_1)
            btnop3.background = this.resources.getDrawable(R.drawable.button_1)
            btnop4.background = this.resources.getDrawable(R.drawable.button_1)
        }

        if (2 == answer) {
            btnop1.background = this.resources.getDrawable(R.drawable.button_1)
            btnop2.background = this.resources.getDrawable(R.drawable.button_2)
            btnop3.background = this.resources.getDrawable(R.drawable.button_1)
            btnop4.background = this.resources.getDrawable(R.drawable.button_1)
        }

        if (3 == answer) {
            btnop1.background = this.resources.getDrawable(R.drawable.button_1)
            btnop2.background = this.resources.getDrawable(R.drawable.button_1)
            btnop3.background = this.resources.getDrawable(R.drawable.button_2)
            btnop4.background = this.resources.getDrawable(R.drawable.button_1)
        }

        if (4 == answer) {
            btnop1.background = this.resources.getDrawable(R.drawable.button_1)
            btnop2.background = this.resources.getDrawable(R.drawable.button_1)
            btnop3.background = this.resources.getDrawable(R.drawable.button_1)
            btnop4.background = this.resources.getDrawable(R.drawable.button_2)
        }
    }

    private fun doStart() {
        if (this.mediaPlayer.isPlaying) {
            this.mediaPlayer.pause()
            this.mediaPlayer.reset()
        } else {
            this.mediaPlayer.start()
        }
    }

    private fun doStop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
    }
}