package com.example.studyenglish.activity.singletonpattern

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.englishstudy.R

class MessageObject private constructor() {
    companion object {
        private var messageObject: MessageObject? = null

        @Synchronized
        fun getInstance(): MessageObject {
            if (messageObject == null) {
                messageObject = MessageObject()
            }
            return messageObject!!
        }
    }

    fun showDialogMessage(gravity: Int, context: Context, message: String, type: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.thongbao)

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAttributes = window?.attributes
        windowAttributes?.gravity = gravity
        window?.attributes = windowAttributes

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true)
        } else {
            dialog.setCancelable(false)
        }

        val txtName: TextView = dialog.findViewById(R.id.dialogError2_name)
        val txtMessage: TextView = dialog.findViewById(R.id.dialogError2_content)
        val btnOke: Button = dialog.findViewById(R.id.btn_dialogError_Oke)

        if (type == 1) txtName.text = "SUCCESS"
        else txtName.text = "ERROR"
        txtMessage.text = message

        btnOke.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}