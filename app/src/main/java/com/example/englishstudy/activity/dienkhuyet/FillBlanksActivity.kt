package com.example.englishstudy.activity.dienkhuyet

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauDienKhuyet
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.CauDienKhuyetViewModel
import com.example.englishstudy.viewmodel.UserViewModel

class FillBlanksActivity : AppCompatActivity() {
    private lateinit var txtscoreDK: TextView
    private lateinit var txtquestcountDK: TextView
    private lateinit var txtquestionDK: TextView
    private lateinit var txttimeDK: TextView
    private lateinit var txtGoiy: TextView
    private lateinit var edtAnswerDK: EditText
    private lateinit var btnconfirm: Button
    private lateinit var btnQuit: Button
    private var cauDienKhuyets: ArrayList<CauDienKhuyet> = ArrayList()
    private var questioncurrent = 0
    private var questiontrue = 0
    private lateinit var answer: String
    private var score = 0
    private var idbo = 0
    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User
    private lateinit var cauDienKhuyetViewModel: CauDienKhuyetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_blanks)

        anhXa()
        getUser()

        val intent = intent
        idbo = intent.getIntExtra("BoDK", 0)
        txttimeDK.text = " "

        addArrayCDK()

        if (cauDienKhuyets.size <= 0) {
            Toast.makeText(
                this,
                "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!",
                Toast.LENGTH_LONG
            ).show()
            val error = Intent(this, DienKhuyetActivity::class.java)
            finish()
            startActivity(error)
        } else {
            showNextQuestion(questioncurrent)

            val countDownTimer = object : CountDownTimer(3000, 2000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    questioncurrent++
                    edtAnswerDK.setTextColor(Color.BLACK)
                    btnconfirm.isEnabled = true
                    edtAnswerDK.setText("")
                    answer = ""
                    showNextQuestion(questioncurrent)
                }
            }

            btnconfirm.setOnClickListener {
                checkAnswer()
                showAnswer()

                countDownTimer.start()
            }

            btnQuit.setOnClickListener {
                val intent = Intent(this, DienKhuyetActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun addArrayCDK() {
        cauDienKhuyetViewModel = ViewModelProvider(this).get(CauDienKhuyetViewModel::class.java)
        cauDienKhuyetViewModel.getListCauDienKhuyetByIdBo(idbo)
            .observe(this, Observer { cauDienKhuyetList ->
                // Update the adapter with the new data
                cauDienKhuyetList?.let {
                    cauDienKhuyets.clear()
                    cauDienKhuyets.addAll(it)
                }
            })
    }

    private fun getCurrUserId(): Int {
        // Retrieve the id of the current user from shared preferences
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        return sharedPref.getInt("currentUserId", 0);
    }

    private fun getUser() {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val currentUserId = getCurrUserId()

        userViewModel.getUser(currentUserId).observe(this, Observer { user ->
            // Update your UI with the user data here
            var idUser = user.id
            var name = user.name
            var email = user.email
            var point = user.point
            var role = user.role

            this.user = User(idUser.toString(), name, email, point, role)
        })
    }

    private fun showNextQuestion(pos: Int) {
        txtquestcountDK.text = "Question: " + (questioncurrent + 1) + "/" + cauDienKhuyets.size + ""
        edtAnswerDK.background = this.resources.getDrawable(R.drawable.bgbtn)

        cauDienKhuyetViewModel.getCauDienKhuyetAtPosition(pos)
            .observe(this, Observer { cauDienKhuyet ->
                // Update the UI with the new data
                cauDienKhuyet?.let {
                    if (pos == cauDienKhuyets.size) {
                        userViewModel.updatePointUser(user.id, score)
                        val intent = Intent(this, FinishDKActivity::class.java)
                        intent.putExtra("scoreDK", score)
                        intent.putExtra("questiontrueDK", questiontrue)
                        intent.putExtra("qcountDK", pos)
                        startActivity(intent)
                    } else {
                        answer = it.dapan
                        txtGoiy.text = it.goiy
                        txtquestionDK.text = it.noidung
                    }
                }
            })
    }

    private fun showAnswer() {
        edtAnswerDK.setText(answer)
        edtAnswerDK.setTextColor(Color.GREEN)
        edtAnswerDK.clearFocus()
    }

    private fun checkAnswer() {
        btnconfirm.isEnabled = false
        if (answer == edtAnswerDK.text.toString()) {
            Toast.makeText(this, "Đáp án chính xác", Toast.LENGTH_SHORT).show()
            edtAnswerDK.setTextColor(Color.GREEN)
            questiontrue++
            score += 5
            txtscoreDK.text = "Score: $score"
            edtAnswerDK.clearFocus()
        } else {
            Toast.makeText(this, "Sai rồi", Toast.LENGTH_SHORT).show()
            edtAnswerDK.setTextColor(Color.RED)
            edtAnswerDK.startAnimation(shakeError())
            edtAnswerDK.clearFocus()
        }
    }

    private fun shakeError(): TranslateAnimation {
        val shake = TranslateAnimation(0f, 10f, 0f, 0f)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7F)
        return shake
    }

    private fun anhXa() {
        txtscoreDK = findViewById(R.id.txtscoreDK)
        txtquestcountDK = findViewById(R.id.txtquestcountDK)
        txtquestionDK = findViewById(R.id.txtquestionDK)
        txttimeDK = findViewById(R.id.txttimeDK)
        edtAnswerDK = findViewById(R.id.AnswerDK)
        btnconfirm = findViewById(R.id.btnconfirmDK)
        txtGoiy = findViewById(R.id.textviewGoiy)
        btnQuit = findViewById(R.id.btnQuitDK)
    }
}