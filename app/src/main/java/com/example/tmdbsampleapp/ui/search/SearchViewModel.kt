package com.example.tmdbsampleapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.domain.GetMoviesUseCase
import com.example.tmdbsampleapp.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentPage = MutableLiveData(1)
    val cachedMovies = mutableListOf<Movie>()

    fun search(query: String): LiveData<Resource<List<Movie>>> {
        return currentPage.switchMap {
            getMovies(query, it)
        }
    }

    fun loadFirstPage() {
        cachedMovies.clear()
        currentPage.value = 1
    }

    fun loadNextPage() {
        currentPage.value = currentPage.value?.plus(1)
    }
}