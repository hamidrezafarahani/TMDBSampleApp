package com.example.tmdbsampleapp.network.apiresponse

import com.example.tmdbsampleapp.network.apierror.ApiError
import retrofit2.Response
import java.lang.reflect.Type

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(throwable: Throwable): ApiResponse<T> {
            return ApiErrorResponse(ApiError.Unknown(throwable))
        }

        fun <T> create(response: Response<T>, dataType: Type): ApiResponse<T> {
            return when {
                response.code() == 200 -> {
                    when (val body = response.body()) {
                        null -> ApiErrorResponse(ApiError.NoContent)
                        else -> ApiSuccessResponse(body, dataType)
                    }
                }

                response.code() == 500 -> ApiErrorResponse(ApiError.Internal)
                else -> ApiErrorResponse(ApiError.BadResponseCode(response.code()))
            }
        }
    }

    data class ApiErrorResponse<T>(val error: ApiError) : ApiResponse<T>()
    data class ApiSuccessResponse<T>(val body: T, val dataType: Type) : ApiResponse<T>()
}