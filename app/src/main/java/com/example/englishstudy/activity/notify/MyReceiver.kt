package com.example.englishstudy.activity.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val actionNotify = intent.getIntExtra("action_notify", 0)

        val intentService = Intent(context, MyService::class.java)
        intentService.putExtra("action_notify_service", actionNotify)

        context.startService(intentService)
    }
}