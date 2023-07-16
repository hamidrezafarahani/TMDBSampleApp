package com.example.tmdbsampleapp.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.domain.GetMoviesUseCase
import com.example.tmdbsampleapp.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase
) : ViewModel() {

    private val currentPage = MutableLiveData(1)
    val cachedMovies = mutableListOf<Movie>()

    val movies: LiveData<Resource<List<Movie>>> = currentPage.switchMap {
        getMovies(it)
    }

    fun loadFirstPage() {
        cachedMovies.clear()
        currentPage.value = 1
    }

    fun loadNextPage() {
        currentPage.value = currentPage.value!!.plus(1)
    }
}