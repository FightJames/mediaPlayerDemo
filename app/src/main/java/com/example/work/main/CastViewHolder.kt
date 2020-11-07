package com.example.work.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.work.castdetail.CastDetailActivity
import com.example.work.model.Cast
import kotlinx.android.synthetic.main.main_cast_item.view.*

class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(cast: Cast) {
        itemView.apply {
            Glide.with(this)
                .load(cast.artworkUrl)
                .fitCenter()
                .into(cast_image)
            cast_name.text = cast.name
            artist_name.text = cast.artistName
        }
        itemView.setOnClickListener {
            it.context.startActivity(CastDetailActivity.getIntent(it.context))
        }
    }
}