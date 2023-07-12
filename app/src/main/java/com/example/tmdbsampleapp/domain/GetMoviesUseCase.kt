package com.example.tmdbsampleapp.domain

import androidx.lifecycle.LiveData
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.data.repository.Repository
import com.example.tmdbsampleapp.network.Resource
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(page: Int): LiveData<Resource<List<Movie>>> {
        return repository.getMovies(page)
    }
}