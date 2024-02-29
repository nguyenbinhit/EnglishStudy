package com.example.englishstudy.activity.sapxepcau

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.model.entity.CauSapXep
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.CauSapXepViewModel
import com.example.englishstudy.viewmodel.UserViewModel
import java.util.Random

class ArrangeSentencesActivity : AppCompatActivity() {
    private var presCounter = 0
    private var keys = arrayOf("part1", "part2", "part3", "part4")
    private var textAnswer = "ENGLISH"
    private val maxPresCounter = 4
    private lateinit var btnquit: Button
    private lateinit var textScreen: TextView
    private lateinit var textTitle: TextView
    private lateinit var tvQuestionCount: TextView
    private lateinit var tvScore: TextView
    private lateinit var cauSapXeps: ArrayList<CauSapXep>
    private lateinit var smallbigforth: Animation
    private var idbo = 0
    private var score = 0
    private var dem = 0
    private var cau = 1
    private lateinit var user: User
    private lateinit var userViewModel: UserViewModel
    private lateinit var cauSapXepViewModel: CauSapXepViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrange_sentences)

        anhXa()

        val intent = intent
        idbo = intent.getIntExtra("idbo", 0)
        cauSapXeps = ArrayList()

        addArraySXC()
        getUser()

        if (cauSapXeps.size <= 0) {
            Toast.makeText(
                this@ArrangeSentencesActivity,
                "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!",
                Toast.LENGTH_LONG
            ).show()
            val error = Intent(this@ArrangeSentencesActivity, SapXepCauActivity::class.java)
            finish()
            startActivity(error)
        } else {
            textAnswer = cauSapXeps[0].dapAn

            smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth)

            keys[0] = cauSapXeps[0].part1
            keys[1] = cauSapXeps[0].part2
            keys[2] = cauSapXeps[0].part3
            keys[3] = cauSapXeps[0].part4

            tvQuestionCount.text = "Question: $cau/${cauSapXeps.size}"
            tvScore.text = "Score: $score"

            keys = shuffleArray(keys)

            dem = 0
            while (dem < keys.size) {
                if (dem < 1) {
                    addView(findViewById(R.id.layoutPart1), keys[dem], findViewById(R.id.editDapAn))
                } else if (dem < 2) {
                    addView(findViewById(R.id.layoutPart2), keys[dem], findViewById(R.id.editDapAn))
                } else if (dem < 3) {
                    addView(findViewById(R.id.layoutPart3), keys[dem], findViewById(R.id.editDapAn))
                } else addView(
                    findViewById(R.id.layoutPart4),
                    keys[dem],
                    findViewById(R.id.editDapAn)
                )
                dem++
            }

            btnquit.setOnClickListener {
                finish()
                val intent = Intent(this@ArrangeSentencesActivity, SapXepCauActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun anhXa() {
        textScreen = findViewById(R.id.textSXC)
        textTitle = findViewById(R.id.textOption)
        tvQuestionCount = findViewById(R.id.tvQuestionSXC)
        tvScore = findViewById(R.id.tvScoreSXC)
        btnquit = findViewById(R.id.btnQuitSXC)
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

    private fun addArraySXC() {
        cauSapXepViewModel = ViewModelProvider(this).get(CauSapXepViewModel::class.java)
        cauSapXepViewModel.getListCauSapXepByIdBo(idbo)
            .observe(this, Observer { cauSapXepList ->
                cauSapXeps.clear()
                cauSapXeps.addAll(cauSapXepList)
            })
    }

    private fun shuffleArray(ar: Array<String>): Array<String> {
        val rnd = Random()
        for (i in ar.indices.reversed()) {
            val index = rnd.nextInt(i + 1)
            val a = ar[index]
            ar[index] = ar[i]
            ar[i] = a
        }
        return ar
    }

    @SuppressLint("SetTextI18n")
    private fun addView(viewParent: LinearLayout, text: String, editText: EditText) {
        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        linearLayoutParams.rightMargin = 5

        val textView = TextView(this)

        textView.layoutParams = linearLayoutParams
        textView.background = this.resources.getDrawable(R.drawable.bgbtn)
        textView.setTextColor(this.resources.getColor(R.color.colorPurple))
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.isClickable = true
        textView.isFocusable = true
        textView.textSize = 30f

        val typeface = Typeface.createFromAsset(assets, "fonts/FredokaOneRegular.ttf")

        textScreen.typeface = typeface
        textTitle.typeface = typeface
        tvQuestionCount.typeface = typeface
        tvScore.typeface = typeface
        editText.typeface = typeface
        textView.typeface = typeface
        textView.setOnClickListener {
            if (presCounter < maxPresCounter) {
                if (presCounter == 0) editText.setText("")

                var chuoi = 0

                if (editText.text.toString() == "" || chuoi == 3) {
                    editText.setText(editText.text.toString() + text)
                    chuoi++
                } else {
                    editText.setText(editText.text.toString() + " " + text)
                    chuoi++
                }

                textView.startAnimation(smallbigforth)
                textView.animate().alpha(0f).duration = 300
                presCounter++
                textView.isClickable = false

                if (presCounter == maxPresCounter) doValidate()
            }
        }
        viewParent.addView(textView)
    }

    @SuppressLint("SetTextI18n")
    private fun doValidate() {
        presCounter = 0

        val editText = findViewById<EditText>(R.id.editDapAn)
        val Part1 = findViewById<LinearLayout>(R.id.layoutPart1)
        val Part2 = findViewById<LinearLayout>(R.id.layoutPart2)
        val Part3 = findViewById<LinearLayout>(R.id.layoutPart3)
        val Part4 = findViewById<LinearLayout>(R.id.layoutPart4)

        if (editText.text.toString() == textAnswer) {
            if (cau == cauSapXeps.size) {
                score += 5
                userViewModel.updatePointUser(user.id, score)
                Toast.makeText(
                    this@ArrangeSentencesActivity,
                    "Hoàn Thành Xuất Sắc!!~(^.^)~",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@ArrangeSentencesActivity, FinishSXCActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("questiontrue", cau)
                intent.putExtra("qcount", cauSapXeps.size)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this@ArrangeSentencesActivity,
                    "Chính xác!!(^.^)",
                    Toast.LENGTH_SHORT
                ).show()
                textAnswer = cauSapXeps[cau].dapAn
                keys[0] = cauSapXeps[cau].part1
                keys[1] = cauSapXeps[cau].part2
                keys[2] = cauSapXeps[cau].part3
                keys[3] = cauSapXeps[cau].part4
                editText.setText("")
                score += 5
                cau++
                tvQuestionCount.text = "Question: $cau/${cauSapXeps.size}"
                tvScore.text = "Score: $score"
            }

        } else {
            Toast.makeText(this@ArrangeSentencesActivity, "Sai rồi!!(T.T)", Toast.LENGTH_SHORT)
                .show()
            editText.setText("")
        }

        keys = shuffleArray(keys)
        Part1.removeAllViews()
        Part2.removeAllViews()
        Part3.removeAllViews()
        Part4.removeAllViews()

        dem = 0
        while (dem < keys.size) {
            if (dem < 1) {
                addView(Part1, keys[dem], editText)
            } else if (dem < 2) {
                addView(Part2, keys[dem], editText)
            } else if (dem < 3) {
                addView(Part3, keys[dem], editText)
            } else addView(Part4, keys[dem], editText)
            dem++
        }
    }
}