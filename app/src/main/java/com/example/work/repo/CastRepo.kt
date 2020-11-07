package com.example.work.repo

import com.example.work.model.CastWrapper
import com.example.work.model.retrofit.CastService

class CastRepo(val castService: CastService) {

    suspend fun getCasts(): CastWrapper = castService.getCasts()

    suspend fun getCastDetail() = castService.getCastDetail()

}