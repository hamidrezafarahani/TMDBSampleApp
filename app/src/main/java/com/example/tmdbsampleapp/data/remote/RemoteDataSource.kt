package com.example.tmdbsampleapp.data.remote

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.network.Resource

interface RemoteDataSource {

    fun getMovies(page: Int): LiveData<Resource<List<Movie>>>
}