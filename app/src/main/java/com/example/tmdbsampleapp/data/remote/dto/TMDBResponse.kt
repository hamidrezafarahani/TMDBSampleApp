package com.example.tmdbsampleapp.data.remote.dto

import com.google.gson.annotations.SerializedName as SN

data class TMDBResponse<out T>(
    @SN("page") val page: Int,
    @SN("results") val movies: List<T>,
    @SN("total_pages") val totalPages: Int,
    @SN("total_results") val totalResults: Int
)