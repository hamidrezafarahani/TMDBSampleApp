package com.example.tmdbsampleapp.data.repository

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.RemoteDataSource
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.network.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
    ): Repository {

    override fun getMovies(page: Int): LiveData<Resource<List<Movie>>> {
        return remoteDataSource.getMovies(page)
    }

    override fun searchMovies(query: String, page: Int): LiveData<Resource<List<Movie>>> {
        return remoteDataSource.searchMovies(query, page)
    }
}