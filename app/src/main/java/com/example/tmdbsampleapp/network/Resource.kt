package com.example.tmdbsampleapp.network

import com.example.tmdbsampleapp.network.apierror.ApiError


enum class Status {
    LOADING, SUCCESS, ERROR
}

sealed class Resource<T>(val status: Status, var data: T? = null) {
    object Loading : Resource<Nothing>(Status.LOADING)
    class Success<T>(data: T) : Resource<T>(Status.SUCCESS, data)
    class Failure<T>(val error: ApiError) : Resource<T>(Status.ERROR)

    companion object {
        fun loading(): Resource<Nothing> = Loading
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> error(error: ApiError): Resource<T> = Failure(error)
    }
}