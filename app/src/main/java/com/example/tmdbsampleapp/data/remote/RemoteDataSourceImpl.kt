package com.example.tmdbsampleapp.data.remote

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.network.Resource
import com.example.tmdbsampleapp.network.apiresponse.ApiListResponse
import com.example.tmdbsampleapp.network.responsehandler.ListResultHandler
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: TMDBService,
) : RemoteDataSource {

    override fun getMovies(page: Int): LiveData<Resource<List<Movie>>> {
        return object : ListResultHandler<Movie>() {

            override fun createCall(): LiveData<ApiListResponse<Movie>> {
                return service.getMovies(page)
            }

        }.asLiveData()
    }

    override fun searchMovies(query: String, page: Int): LiveData<Resource<List<Movie>>> {
        return object : ListResultHandler<Movie>() {

            override fun createCall(): LiveData<ApiListResponse<Movie>> {
                return service.searchMovies(query, page)
            }

        }.asLiveData()
    }
}