package com.example.work.repo

import com.example.work.model.retrofit.RetrofitManager

object RepoFactory {
    private val instanceMap = HashMap<Class<*>, Any?>()

    fun <T> get(input: Class<T>): T {
        return instanceMap[input] as? T ?: return create(input).apply { instanceMap[input] = this }
    }

    private fun <T> create(input: Class<T>): T =
        when {
            input.isAssignableFrom(CastRepo::class.java) -> CastRepo(RetrofitManager.castService) as T
            else -> throw UnsupportedOperationException()
        }
}

