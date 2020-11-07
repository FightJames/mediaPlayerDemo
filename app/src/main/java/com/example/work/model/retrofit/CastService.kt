package com.example.work.model.retrofit

import com.example.work.model.CastList
import retrofit2.http.GET

interface CastService {

    @GET("/getcasts")
    suspend fun getCasts(): CastList

    @GET("/getcastdetail")
    suspend fun getCastDetail()

}