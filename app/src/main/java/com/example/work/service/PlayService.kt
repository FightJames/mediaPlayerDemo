package com.example.work.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class PlayService : Service(), PlayInterface {

    lateinit var mediaPlayer: MediaPlayer
    private var currentSource: String = ""

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
        Log.d("Service", "onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("Service", "onBind")
        return PlayBinder()
    }

    inner class PlayBinder : Binder() {
        fun getPlayService(): PlayInterface =
            this@PlayService
    }

    override fun getCurrentSong(): String = currentSource

    override fun setSong(url: String) {
        mediaPlayer.stop()
        mediaPlayer.setDataSource(url)
        currentSource = url
    }

    override fun seekTo(millisecond: Int) {
        mediaPlayer.seekTo(millisecond)
    }

    override fun getCurrentMilliSec(): Int =
        mediaPlayer.currentPosition

    override fun getTotalMilliSec(): Int =
        mediaPlayer.duration

    override fun start() =
        mediaPlayer.start()

    override fun stop() =
        mediaPlayer.stop()

    override fun pause() =
        mediaPlayer.pause()

    override fun reset() =
        mediaPlayer.reset()

    override fun isPlaying(): Boolean =
        mediaPlayer.isPlaying

    companion object {
        fun getIntent(context: Context) =
            Intent(context, PlayService::class.java)
    }
}