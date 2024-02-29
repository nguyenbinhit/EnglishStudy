package com.example.englishstudy.activity.luyennghe

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.Gravity
import com.example.studyenglish.activity.singletonpattern.MessageObject

object MediaPlayerUtils {
    const val URL_MEDIA_SAMPLE =
        "https://github.com/nguyenbinhit/Android/blob/main/LuyenNghe_Hey%20Mama%20-%20David%20Guetta%20feat_%20Nicki%20Mina.mp3"
    const val LOG_TAG = "MediaPlayerTutorial"

    fun playURLMedia(context: Context, mediaPlayer: MediaPlayer, videoURL: String) {
        try {
            Log.i(LOG_TAG, "Media URL: $videoURL")

            val uri = Uri.parse(videoURL)
            mediaPlayer.setDataSource(context, uri)
            mediaPlayer.prepareAsync()

        } catch (e: Exception) {
            val messageObject = MessageObject.getInstance()
            Log.e(LOG_TAG, "Error Play URL Media: ${e.message}")
            messageObject.showDialogMessage(
                Gravity.CENTER,
                context,
                "Error Play URL Media: ${e.message}",
                0
            )
            e.printStackTrace()
        }
    }
}