package com.example.tmdbsampleapp.network.apierror

import android.content.Context
import com.example.tmdbsampleapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class GlobalApiErrorHandler @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun handleError(error: ApiError, listener: (err: ApiError, message: String) -> Unit) {
        listener(error, getErrorDescription(error))
    }

    private fun getErrorDescription(error: ApiError): String = when (error) {
        is ApiError.BadResponseCode, ApiError.NoContent, ApiError.Parse ->
            context.getString(R.string.err_bad_response)

        is ApiError.Internal -> context.getString(R.string.err_server_unreachable)
        is ApiError.NoConnection -> context.getString(R.string.err_no_internet)
        is ApiError.HandledError -> error.error.statusMessage ?: "UNKNOWN ERROR"
        is ApiError.Unknown -> when (error.throwable) {
            is UnknownHostException -> context.getString(R.string.err_unknown_host)
            is SSLHandshakeException -> context.getString(R.string.err_ssl_handshake)
            else -> "خطایی در دسترسی به سرور رخ داده است. لطفا دوباره تلاش کنید."
        }
    }
}
