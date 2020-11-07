package com.example.work

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.work.castdetail.CastDetailViewModel
import com.example.work.main.MainViewModel
import com.example.work.repo.CastRepo
import com.example.work.repo.RepoFactory

class CastViewModelFactory(
    application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(RepoFactory.get(CastRepo::class.java)) as T

            modelClass.isAssignableFrom(CastDetailViewModel::class.java) ->
                CastDetailViewModel(RepoFactory.get(CastRepo::class.java)) as T
            else -> super.create(modelClass)
        }
}