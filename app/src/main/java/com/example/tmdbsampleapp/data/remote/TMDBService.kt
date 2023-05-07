package com.example.tmdbsampleapp.data.remote

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.data.remote.dto.TMDBResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("popular")
    fun getMovies(@Query("page") page: Int): LiveData<ApiListResponse<Movie>>

    @GET("popular")
    fun getPopularMovies(@Query("page") page: Int): LiveData<ApiResponse<TMDBResponse<Movie>>>
}