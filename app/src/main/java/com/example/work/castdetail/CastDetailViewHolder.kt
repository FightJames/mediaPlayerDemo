package com.example.work.castdetail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.work.model.Content
import com.example.work.player.PlayActivity
import com.example.work.player.PlayArgs
import kotlinx.android.synthetic.main.detail_cast_item.view.*

class CastDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(content: Content, largeImageUrl: String) {
        itemView.apply {
            detail_item_name.text = content.title
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