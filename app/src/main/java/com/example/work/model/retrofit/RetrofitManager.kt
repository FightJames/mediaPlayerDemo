package com.example.work.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private val baseUrl = "http://demo4491005.mockable.io/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val castService: CastService by lazy { retrofit.create(CastService::class.java) }
}