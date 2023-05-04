package com.example.tmdbsampleapp.network.responsehandler

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiResponse

class ResultHandlerFactory {

    fun <T> create(f: () -> LiveData<ApiResponse<T>>): ResultHandler<T> {
        return object : ResultHandler<T>() {
            override fun createCall(): LiveData<ApiResponse<T>> {
                return f()
            }
        }
    }

    fun <T> create(f: () -> LiveData<ApiListResponse<T>>): ListResultHandler<T> {
        return object : ListResultHandler<T>() {
            override fun createCall(): LiveData<ApiListResponse<T>> {
                return f()
            }
        }
    }
}