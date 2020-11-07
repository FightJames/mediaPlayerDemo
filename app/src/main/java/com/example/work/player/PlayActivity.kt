package com.example.work.player

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.work.R
import com.example.work.util.ArgsCreator
import com.example.work.util.putArgs

class PlayActivity : AppCompatActivity() {
    val playArgs by ArgsCreator<PlayArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
    }

    companion object {
        fun getIntent(context: Context, playArgs: PlayArgs) =
            Intent(context, PlayActivity::class.java).apply { this.putArgs(playArgs) }
    }
}