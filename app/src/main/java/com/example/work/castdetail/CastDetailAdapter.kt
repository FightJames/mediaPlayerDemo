package com.example.work.castdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.work.R
import com.example.work.model.Content

class CastDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<Content> = emptyList()
    private var largeImageUrl: String = ""

    fun setData(input: List<Content>, largeImageUrl: String) {
        data = input
        this.largeImageUrl = largeImageUrl
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CastDetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.detail_cast_item, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CastDetailViewHolder)?.bind(data[position], largeImageUrl)
    }
}