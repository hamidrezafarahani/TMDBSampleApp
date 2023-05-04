package com.example.tmdbsampleapp.network.apierror

import com.example.tmdbsampleapp.data.remote.dto.ResponseError

sealed class ApiError {

    class Unknown(val throwable: Throwable) : ApiError()
    class BadResponseCode(val responseCode: Int) : ApiError()
    object Parse : ApiError()
    object NoConnection : ApiError()
    object NoContent : ApiError()
    object Internal : ApiError()
    sealed class HandledError(val error: ResponseError) : ApiError() {
        companion object {
            const val BAD_REQUEST = 400
            const val FORBIDDEN = 403
            const val NOT_FOUND = 404
            const val REQUEST_TIMEOUT = 408
            const val CONFLICT = 409
        }

        class BadRequest(message: String) : HandledError(ResponseError(BAD_REQUEST, message))
        class Forbidden(message: String) : HandledError(ResponseError(FORBIDDEN, message))
        class NotFound(message: String) : HandledError(ResponseError(NOT_FOUND, message))
        class Global(globalError: ResponseError) : HandledError(globalError)
    }
}