package com.example.work.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.work.R
import com.example.work.service.PlayInterface
import com.example.work.service.PlayService
import com.example.work.util.ArgsCreator
import com.example.work.util.putArgs
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.coroutines.delay

class PlayActivity : AppCompatActivity() {
    val playArgs by ArgsCreator<PlayArgs>()
    var playService: PlayInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        play_progressbar.visibility = View.VISIBLE
        initPlayService()
        initView()
    }

    private fun initView() {
        play_content_name_text.text = playArgs.title
        Glide.with(this)
            .load(playArgs.largeImageUrl)
            .fitCenter()
            .into(play_cast_image)

        play_back_image.setOnClickListener { finish() }
        play_play_image.setOnClickListener { view ->
            playService?.let { player ->
                if (!view.isSelected) {
                    Log.d("James"," view.isSelected = ${view.isSelected}")
                    if (player.getCurrentSong() != playArgs.songUrl) {
                        Log.d("James","play btn reset, ${playArgs.songUrl}")
                        player.reset()
                        player.setSong(playArgs.songUrl)
                        player.start()
                        play_total_text.text = formateTime(player.getCurrentMilliSec())
                    } else {
                        player.start()
                    }
                } else {
                    player.pause()
                }
                view.isSelected = !view.isSelected
            }
        }
        lifecycleScope.launchWhenStarted {
            while (true) {
                playService?.let {
                    play_cur_text.text = formateTime(it.getCurrentMilliSec())
                }
                delay(500)
            }
        }
    }

    private fun formateTime(millisec: Int): String {
        val min = (millisec / 1000) / 60
        val sec = ((millisec / 1000) % 60)
        return "$min : $sec"
    }

    private fun initPlayService() {
        startService(PlayService.getIntent(this))
        bindService(PlayService.getIntent(this), object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                play_progressbar.visibility = View.GONE
                playService = (service as? PlayService.PlayBinder)?.getPlayService()?.apply {
                    play_play_image.isSelected = getCurrentSong() != playArgs.songUrl
                    if (play_play_image.isSelected) play_play_image.isSelected = isPlaying()
                    play_total_text.text = this@PlayActivity.formateTime(getCurrentMilliSec())
                }
                Log.d("James_debug", " $playService")
            }
        }, Context.BIND_AUTO_CREATE)
    }

    companion object {
        fun getIntent(context: Context, playArgs: PlayArgs) =
            Intent(context, PlayActivity::class.java).apply { this.putArgs(playArgs) }
    }
}