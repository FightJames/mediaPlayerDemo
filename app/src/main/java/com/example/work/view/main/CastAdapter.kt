package com.example.work.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.work.R
import com.example.work.model.Cast

class CastAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<Cast> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CastViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_cast_item, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CastViewHolder)?.bind(data[position])
    }

    fun setData(input: List<Cast>) {
        data = input
        notifyDataSetChanged()
    }
}