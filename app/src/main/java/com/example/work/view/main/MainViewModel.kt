package com.example.work.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.work.model.Cast
import com.example.work.repo.CastRepo
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val castRepo: CastRepo) : ViewModel() {
    val castsData = MutableLiveData<List<Cast>>()
    private var fetchCastJob: Job = CompletableDeferred(Unit)

    fun fetchCasts() {
        if (fetchCastJob.isActive) return
        fetchCastJob = viewModelScope.launch {
            castsData.value = castRepo.getCasts().postcastWrapper.list
        }
    }
}