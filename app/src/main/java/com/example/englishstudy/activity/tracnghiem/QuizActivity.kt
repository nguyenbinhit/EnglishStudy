package com.example.englishstudy.activity.tracnghiem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauTracNghiem
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.CauTracNghiemViewModel
import com.example.englishstudy.viewmodel.UserViewModel

class QuizActivity : AppCompatActivity() {
    private lateinit var txtscore: TextView
    private lateinit var txtquestcount: TextView
    private lateinit var txtquestion: TextView
    private lateinit var txttime: TextView
    private lateinit var rdgchoices: RadioGroup
    private lateinit var btnop1: RadioButton
    private lateinit var btnop2: RadioButton
    private lateinit var btnop3: RadioButton
    private lateinit var btnop4: RadioButton
    private lateinit var btnconfirm: Button
    private lateinit var btnquit: Button
    private lateinit var cauTracNghiems: ArrayList<CauTracNghiem>
    private lateinit var user: User
    private var questioncurrent = 0
    private var questiontrue = 0
    private var answer = 0
    private var score = 0
    private var idbo = 0
    private lateinit var userViewModel: UserViewModel
    private lateinit var cauTracNghiemViewModel: CauTracNghiemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        anhXa()
        getUser()

        idbo = intent.getIntExtra("Bo", 0)
        txttime.text = " "
        cauTracNghiems = ArrayList()

        addArrayCTN()

        if (cauTracNghiems.size <= 0) {
            Toast.makeText(
                this,
                "Nội dung sẽ cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!",
                Toast.LENGTH_LONG
            ).show()
            val error = Intent(this, TracNghiemActivity::class.java)
            finish()
            startActivity(error)
        } else {
            showNextQuestion(questioncurrent, cauTracNghiems)

            val countDownTimer = object : CountDownTimer(3000, 300) {
                override fun onTick(millisUntilFinished: Long) {
                    showAnswer()
                }

                override fun onFinish() {
                    btnconfirm.isEnabled = true
                    showNextQuestion(questioncurrent, cauTracNghiems)
                }
            }

            btnconfirm.setOnClickListener {
                checkAns()
                questioncurrent++
                countDownTimer.start()
            }

            btnquit.setOnClickListener {
                val intent = Intent(this@QuizActivity, TracNghiemActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun anhXa() {
        txtscore = findViewById(R.id.txtscoreTN)
        txtquestcount = findViewById(R.id.txtquestcountTN)
        txtquestion = findViewById(R.id.txtquestionTN)
        txttime = findViewById(R.id.txttimeTN)
        rdgchoices = findViewById(R.id.radiochoices)
        btnop1 = findViewById(R.id.op1)
        btnop2 = findViewById(R.id.op2)
        btnop3 = findViewById(R.id.op3)
        btnop4 = findViewById(R.id.op4)
        btnconfirm = findViewById(R.id.btnconfirmTN)
        btnquit = findViewById(R.id.btnQuitTN)
    }

    private fun addArrayCTN() {
        cauTracNghiemViewModel = ViewModelProvider(this).get(CauTracNghiemViewModel::class.java)
        cauTracNghiemViewModel.getListCauTracNghiemByIdBo(idbo)
            .observe(this, Observer { cauTracNghiems ->
                this.cauTracNghiems.clear()
                this.cauTracNghiems.addAll(cauTracNghiems)
            })
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

    private fun showNextQuestion(pos: Int, cauTracNghiems: ArrayList<CauTracNghiem>) {
        txtquestcount.text = "Question: " + (questioncurrent + 1) + "/" + cauTracNghiems.size + ""
        rdgchoices.clearCheck()
        btnop1.background = this.resources.getDrawable(R.drawable.bgbtn)
        btnop2.background = this.resources.getDrawable(R.drawable.bgbtn)
        btnop3.background = this.resources.getDrawable(R.drawable.bgbtn)
        btnop4.background = this.resources.getDrawable(R.drawable.bgbtn)
        if (pos == cauTracNghiems.size) {
            userViewModel.updatePointUser(user.id, score)
            val intent = Intent(this, FinishQuizActivity::class.java)
            intent.putExtra("score", score)
            intent.putExtra("questiontrue", questiontrue)
            intent.putExtra("qcount", pos)
            startActivity(intent)
        } else {
            answer = Integer.valueOf(cauTracNghiems[pos].dapAnDung)
            txtquestion.text = cauTracNghiems[pos].noiDung
            btnop1.text = cauTracNghiems[pos].dapAnA
            btnop2.text = cauTracNghiems[pos].dapAnB
            btnop3.text = cauTracNghiems[pos].dapAnC
            btnop4.text = cauTracNghiems[pos].dapAnD
        }
    }

    private fun checkAns() {
        btnconfirm.isEnabled = false
        if (btnop1.isChecked) {
            if (1 == answer) {
                questiontrue++
                score += 5
            }
        }
        if (btnop2.isChecked) {
            if (2 == answer) {
                questiontrue++
                score += 5
            }
        }
        if (btnop3.isChecked) {
            if (3 == answer) {
                questiontrue++
                score += 5
            }
        }
        if (btnop4.isChecked) {
            if (4 == answer) {
                questiontrue++
                score += 5
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
}