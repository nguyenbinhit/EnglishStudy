package com.example.englishstudy.activity.hoctuvung

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.englishstudy.R
import com.example.englishstudy.activity.luyennghe.MediaPlayerUtils
import com.example.englishstudy.model.entity.TuVung
import com.example.englishstudy.model.entity.User
import com.example.englishstudy.viewmodel.TuVungViewMModel
import com.example.englishstudy.viewmodel.UserViewModel
import java.util.Random

class WordMattersActivity : AppCompatActivity() {
    private var presCounter = 0
    private var keys = arrayOf("a", "b", "c", "d", "e", "f", "g")
    private lateinit var mediaPlayer: MediaPlayer
    private var URL =
        "https://github.com/nguyenbinhit/Android/blob/main/LuyenNghe_Hey%20Mama%20-%20David%20Guetta%20feat_%20Nicki%20Mina.mp3"
    private var UL = "hello"
    private var textAnswer = "ENGLISH"
    private var maxPresCounter = 0
    private lateinit var textScreen: TextView
    private lateinit var textQuestion: TextView
    private lateinit var textTitle: TextView
    private lateinit var tvWordCount: TextView
    private lateinit var tvScore: TextView
    private lateinit var ListenTV: ImageButton
    private lateinit var imgview: ImageView
    private lateinit var btnquit: Button
    private lateinit var DStuvung: ArrayList<TuVung>
    private lateinit var smallbigforth: Animation
    private var idbo = 0
    private var score = 0
    private var dem = 0
    private var tu = 1
    private lateinit var smalltobig: Animation
    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_matters)
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig)

        anhXa()
        getUser()

        val intent = intent
        idbo = intent.getIntExtra("idbo",0)
        DStuvung = ArrayList()

        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnPreparedListener { mp -> mp.start() }

        val tuVungViewModel = ViewModelProvider(this).get(TuVungViewMModel::class.java)
        tuVungViewModel.getListTuVungByIdBo(idbo).observe(this, Observer { tuVungs ->
            DStuvung.clear()
            DStuvung.addAll(tuVungs)

            // Move the dependent code into the observer block
            if (DStuvung.isNotEmpty()) {
                val img = DStuvung[0].anh?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
                imgview.setImageBitmap(img)
                imgview.startAnimation(smalltobig)
                textQuestion.text = "(${DStuvung[0].loaiTu}) - (${DStuvung[0].dichNghia})"
                tvWordCount.text = "Word: $tu/${DStuvung.size}"
                tvScore.text = "Score: $score"

                textAnswer = DStuvung[0].dapAn
                URL = DStuvung[0].audio

                smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth)

                for (i in textAnswer.indices) {
                    keys[i] = textAnswer[i].toString()
                }

                dem = 0
                while (dem < keys.size) {
                    when {
                        dem < 4 -> {
                            addView(findViewById(R.id.layoutParent1), keys[dem], findViewById(R.id.editText))
                        }
                        dem < 8 -> {
                            addView(findViewById(R.id.layoutParent2), keys[dem], findViewById(R.id.editText))
                        }
                        else -> {
                            addView(findViewById(R.id.layoutParent3), keys[dem], findViewById(R.id.editText))
                        }
                    }
                    dem++
                }

                maxPresCounter = textAnswer.length

                ListenTV.setOnClickListener {
                    doStop()
                    if (UL == URL) MediaPlayerUtils.playURLMedia(this, mediaPlayer, UL)
                    else {
                        val mediaURL = URL
                        UL = URL
                        MediaPlayerUtils.playURLMedia(this, mediaPlayer, mediaURL)
                    }
                }

                btnquit.setOnClickListener {
                    finish()
                    val intent = Intent(this, HocTuVungActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    private fun anhXa() {
        textQuestion = findViewById(R.id.textQuestion)
        imgview = findViewById(R.id.imgview)
        textScreen = findViewById(R.id.textScreen)
        textTitle = findViewById(R.id.textTitle)
        tvWordCount = findViewById(R.id.tvWord)
        tvScore = findViewById(R.id.tvScore)
        ListenTV = findViewById(R.id.ListenTuVung)
        btnquit = findViewById(R.id.btnQuitHTV)
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
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        linearLayoutParams.rightMargin = 30

        val textView = TextView(this)

        textView.layoutParams = linearLayoutParams
        textView.background = this.resources.getDrawable(R.drawable.bgpink)
        textView.setTextColor(this.resources.getColor(R.color.colorPurple))
        textView.gravity = Gravity.CENTER
        textView.text = text
        textView.isClickable = true
        textView.isFocusable = true
        textView.textSize = 35f

        val typeface = Typeface.createFromAsset(assets, "fonts/fredokaoneregular.ttf")

        textScreen.typeface = typeface
        textTitle.typeface = typeface
        editText.typeface = typeface
        textView.typeface = typeface
        tvScore.typeface = typeface
        tvWordCount.typeface = typeface

        textView.setOnClickListener {
            if (presCounter < maxPresCounter) {
                if (presCounter == 0)
                    editText.setText("")

                editText.setText(editText.text.toString() + text)
                textView.startAnimation(smallbigforth)
                textView.animate().alpha(0f).duration = 300
                presCounter++
                textView.isClickable = false

                if (presCounter == maxPresCounter)
                    doValidate()
            }
        }
        viewParent.addView(textView)
    }

    private fun doValidate() {
        presCounter = 0

        val editText = findViewById<EditText>(R.id.editText)
        val linearLayout1 = findViewById<LinearLayout>(R.id.layoutParent1)
        val linearLayout2 = findViewById<LinearLayout>(R.id.layoutParent2)
        val linearLayout3 = findViewById<LinearLayout>(R.id.layoutParent3)

        if (editText.text.toString() == textAnswer) {
            if (tu == DStuvung.size) {
                score += 5
                userViewModel.updatePointUser(user.id, score)

                Toast.makeText(this, "Hoàn Thành Xuất Sắc!!~(^.^)~", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, FinishHTVActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("questiontrue", tu)
                intent.putExtra("qcount", DStuvung.size)
                startActivity(intent)
                finish()
            } else {
                if (tu > 0) doStop()

                Toast.makeText(this, "Chính xác!!(^.^)", Toast.LENGTH_SHORT).show()

                val img =
                    DStuvung[tu].anh?.let { BitmapFactory.decodeByteArray(DStuvung[tu].anh, 0, it.size) }
                imgview.setImageBitmap(img)
                imgview.startAnimation(smalltobig)
                textQuestion.text = "(${DStuvung[tu].loaiTu}) - (${DStuvung[tu].dichNghia})"
                textAnswer = DStuvung[tu].dapAn
                URL = DStuvung[tu].audio

                for (i in textAnswer.indices) {
                    keys[i] = textAnswer[i].toString()
                }

                maxPresCounter = textAnswer.length
                editText.setText("")
                score += 5
                tu++
                tvWordCount.text = "Word: $tu/${DStuvung.size}"
                tvScore.text = "Score: $score"
            }
        } else {
            Toast.makeText(this, "Sai rồi!!(T.T)", Toast.LENGTH_SHORT).show()
            editText.setText("")
        }

        keys = shuffleArray(keys)
        linearLayout1.removeAllViews()
        linearLayout2.removeAllViews()
        linearLayout3.removeAllViews()

        dem = 0
        while (dem < keys.size) {
            when {
                dem < 4 -> {
                    addView(linearLayout1, keys[dem], editText)
                }

                dem < 8 -> {
                    addView(linearLayout2, keys[dem], editText)
                }

                else -> {
                    addView(linearLayout3, keys[dem], editText)
                }
            }
            dem++
        }
    }

    private fun doStop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
    }
}