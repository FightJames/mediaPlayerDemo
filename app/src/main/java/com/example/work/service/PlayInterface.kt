package com.example.work.service

interface PlayInterface {
    fun setSong(url: String)

    fun seekTo(millisecond: Int)

    fun getCurrentMilliSec(): Int

    fun getTotalMilliSec(): Int

    fun getCurrentSong(): String

    fun start()

    fun stop()

    fun pause()

    fun reset()

    fun isPlaying() :Boolean
}