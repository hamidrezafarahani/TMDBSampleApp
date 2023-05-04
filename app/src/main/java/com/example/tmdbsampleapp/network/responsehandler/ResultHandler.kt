package com.example.tmdbsampleapp.network.responsehandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.tmdbsampleapp.network.Resource
import com.example.tmdbsampleapp.network.TypeConverter.getResultAsObject
import com.example.tmdbsampleapp.network.apierror.ApiError
import com.example.tmdbsampleapp.network.apiresponse.ApiResponse

abstract class ResultHandler<T> {

    private val result = MediatorLiveData<Resource<T>>()

    init {
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        val response: LiveData<ApiResponse<T>> = createCall()
        @Suppress("UNCHECKED_CAST")
        result.value = Resource.loading() as Resource<T>
        result.addSource(response) {
            when (it) {
                is ApiResponse.ApiSuccessResponse -> {
                    val res = getResultAsObject(it)
                    if (res == null) {
                        result.value = Resource.error(ApiError.Parse)
                        return@addSource
                    }
                    result.value = Resource.success(res)
                }

                is ApiResponse.ApiErrorResponse -> {
                    result.value = Resource.error(it.error)
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<T>>

    abstract fun createCall(): LiveData<ApiResponse<T>>
}