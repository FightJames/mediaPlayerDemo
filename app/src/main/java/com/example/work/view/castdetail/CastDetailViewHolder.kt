package com.example.work.view.castdetail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.work.model.Content
import com.example.work.view.player.PlayActivity
import com.example.work.view.player.PlayArgs
import com.example.work.service.PlayInterface
import kotlinx.android.synthetic.main.detail_cast_item.view.*

class CastDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        content: Content,
        largeImageUrl: String,
        playService: PlayInterface?,
        isCurrentSong: Boolean
    ) {
        itemView.apply {
            detail_play_image.isSelected = isCurrentSong && playService?.isPlaying() ?: false
            detail_item_name.text = content.title
            detail_play_image.setOnClickListener {
                playService?.let {
                    if (isCurrentSong) {
                        if (it.isPlaying()) {
                            it.pause()
                        } else {
                            it.start()
                        }
                    } else {
                        it.reset()
                        it.setSong(content.url)
                        it.prepare()
                        it.start()
                    }
                    detail_play_image.isSelected = !detail_play_image.isSelected
                }
            }
        }

        itemView.setOnClickListener {
            it.context.startActivity(
                PlayActivity.getIntent(
                    it.context,
                    PlayArgs(content.url, content.title, largeImageUrl)
                )
            )
        }
    }
}