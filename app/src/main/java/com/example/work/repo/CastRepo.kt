package com.example.work.repo

import com.example.work.model.CastList
import com.example.work.model.retrofit.CastService
import retrofit2.http.GET

class CastRepo(val castService: CastService) {

    suspend fun getCasts(): CastList = castService.getCasts()

    suspend fun getCastDetail() = castService.getCastDetail()

}