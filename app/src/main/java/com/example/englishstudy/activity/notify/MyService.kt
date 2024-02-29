package com.example.englishstudy.activity.notify

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.example.englishstudy.R
import com.example.englishstudy.activity.taikhoan.LoginActivity

class MyService : Service() {
    private val ACTION_OK = 1

    override fun onCreate() {
        super.onCreate()
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var actionNotify = -1
        try {
            if (intent != null) {
                actionNotify = intent.getIntExtra("action_notify_service", 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (actionNotify != 0 && actionNotify != -1) {
            handleActionNotify(actionNotify)
        } else {
            sendNotification()
        }

        return START_NOT_STICKY
    }

    private fun handleActionNotify(action: Int) {
        when (action) {
            ACTION_OK -> okFunction()
        }
    }

    private fun okFunction() {
        // getting the static instance of activity
//        LoginActivity.instance?.clickStopService()
    }

    private fun sendNotification() {
        val intent = Intent(this, LoginActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        @SuppressLint("RemoteViewLayout") val remoteViews =
            RemoteViews(packageName, R.layout.notification_login)

        remoteViews.setOnClickPendingIntent(R.id.btn_ok, getPendingIntent(this, ACTION_OK))

        val notification: Notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .build()

        startForeground(1, notification)
    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent {
        val intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("action_notify", action)

        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}