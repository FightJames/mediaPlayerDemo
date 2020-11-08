package com.example.work.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
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
                    if (player.getCurrentSong() != playArgs.songUrl) {
                        player.reset()
                        player.setSong(playArgs.songUrl)
                        player.prepare()
                        player.start()
                        player.getTotalMilliSec().let {
                            play_total_text.text = formateTime(it)
                            play_seekbar.max = it
                        }
                    } else {
                        player.start()
                    }
                } else {
                    player.pause()
                }
                view.isSelected = !view.isSelected
            }
        }
        var isTouchSeekbar = false
        lifecycleScope.launchWhenStarted {
            while (true) {
                playService?.let {
                    play_cur_text.text = formateTime(it.getCurrentMilliSec())
                    if (it.getTotalMilliSec() != 0 && !isTouchSeekbar)
                        play_seekbar.progress = it.getCurrentMilliSec()
                }
                delay(500)
            }
        }
        play_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                isTouchSeekbar = true
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isTouchSeekbar = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                playService?.let {
                    it.seekTo(seekBar.progress)
                }
                isTouchSeekbar = false
            }
        })
        play_forward_image.setOnClickListener {
            playService?.let {
                it.seekTo(it.getCurrentMilliSec() + 30000)
            }
        }

        play_backward_image.setOnClickListener {
            playService?.let {
                it.seekTo(it.getCurrentMilliSec() - 30000)
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
                    if (getCurrentSong() == playArgs.songUrl) {
                        play_play_image.isSelected = isPlaying()
                        getTotalMilliSec().let {
                            play_seekbar.max = it
                            play_total_text.text = formateTime(it)
                        }
                        getCurrentMilliSec().let {
                            play_seekbar.progress = it
                            play_cur_text.text = formateTime(it)
                        }
                    }
                }
            }
        }, Context.BIND_AUTO_CREATE)
    }

    companion object {
        fun getIntent(context: Context, playArgs: PlayArgs) =
            Intent(context, PlayActivity::class.java).apply { this.putArgs(playArgs) }
    }
}