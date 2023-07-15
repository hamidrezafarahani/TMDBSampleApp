package com.example.tmdbsampleapp.data.remote

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.data.remote.dto.TMDBResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse
import com.example.tmdbsampleapp.network.apiresponse.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/popular")
    fun getMovies(@Query("page") page: Int): LiveData<ApiListResponse<Movie>>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): LiveData<ApiResponse<TMDBResponse<Movie>>>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): LiveData<ApiListResponse<Movie>>
}