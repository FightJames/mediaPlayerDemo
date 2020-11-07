package com.example.work.model.retrofit

import com.example.work.model.CastDetailsInfo
import com.example.work.model.CastDetailsWrapper
import com.example.work.model.CastWrapper
import retrofit2.http.GET

interface CastService {

    @GET("/getcasts")
    suspend fun getCasts(): CastWrapper

    @GET("/getcastdetail")
    suspend fun getCastDetail(): CastDetailsWrapper

}