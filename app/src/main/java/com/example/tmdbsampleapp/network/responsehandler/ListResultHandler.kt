package com.example.tmdbsampleapp.network.responsehandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.tmdbsampleapp.network.Resource
import com.example.tmdbsampleapp.network.TypeConverter.getResultAsList
import com.example.tmdbsampleapp.network.apierror.ApiError
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse

abstract class ListResultHandler<T> {

    private val result = MediatorLiveData<Resource<List<T>>>()

    init {
        fetchFromNetwork()
    }

    @Suppress("UNCHECKED_CAST")
    private fun fetchFromNetwork() {
        val response: LiveData<ApiListResponse<T>> = createCall()
        result.value = Resource.loading() as Resource<List<T>>
        result.addSource(response) {
            when (it) {
                is ApiListResponse.ApiSuccessListResponse<*> -> {
                    val res = getResultAsList<T>(it.dataType, it.body.movies)
                    if (res == null) {
                        result.value = Resource.error(ApiError.Parse)
                        return@addSource
                    }
                    result.value = Resource.success(res)
                }

                is ApiListResponse.ApiErrorListResponse<*> -> {
                    result.value = Resource.error(it.error)
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<List<T>>>

    abstract fun createCall(): LiveData<ApiListResponse<T>>
}