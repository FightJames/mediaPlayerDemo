package com.example.work.castdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.work.model.CastDetailsInfo
import com.example.work.repo.CastRepo
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CastDetailViewModel(private val castRepo: CastRepo) : ViewModel() {
    val castDetailsData = MutableLiveData<CastDetailsInfo>()
    private var fetchCastDetailsJob: Job = CompletableDeferred(Unit)

    fun fetchCastDetails() {
        if (fetchCastDetailsJob.isActive) return
        fetchCastDetailsJob = viewModelScope.launch {
            castDetailsData.value = castRepo.getCastDetail().castDetails.collection
        }
    }
}