package com.example.tmdbsampleapp.network.apiresponse

import com.example.tmdbsampleapp.data.remote.dto.TMDBResponse
import com.example.tmdbsampleapp.network.apierror.ApiError
import retrofit2.Response
import java.lang.reflect.Type

sealed class ApiListResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiListResponse<TMDBResponse<T>> {
            return ApiErrorListResponse(ApiError.Unknown(error))
        }

        fun <T> create(
            response: Response<TMDBResponse<T>>,
            dataType: Type
        ): ApiListResponse<TMDBResponse<T>> {
            return when {
                response.code() == 200 -> {
                    when (val body = response.body()) {
                        null -> ApiErrorListResponse(ApiError.NoContent)
                        else -> ApiSuccessListResponse(body, dataType)
                    }
                }

                response.code() == 500 -> ApiErrorListResponse(ApiError.Internal)
                else -> ApiErrorListResponse(ApiError.BadResponseCode(response.code()))
            }
        }
    }

    data class ApiErrorListResponse<T>(val error: ApiError) : ApiListResponse<TMDBResponse<T>>()
    data class ApiSuccessListResponse<T>(
        val body: TMDBResponse<T>,
        val dataType: Type
    ) : ApiListResponse<TMDBResponse<T>>()
}