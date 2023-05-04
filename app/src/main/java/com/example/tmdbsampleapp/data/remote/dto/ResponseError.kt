package com.example.tmdbsampleapp.data.remote.dto


import com.google.gson.annotations.SerializedName as SN

data class ResponseError(
    @SN("status_code") val statusCode: Int,
    @SN("status_message") val statusMessage: String?,
    @SN("success") var success: Boolean = false
)