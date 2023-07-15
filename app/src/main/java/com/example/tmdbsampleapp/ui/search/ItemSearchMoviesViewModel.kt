package com.example.tmdbsampleapp.ui.search

import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.shared.Constants

class ItemSearchMoviesViewModel(private val movie: Movie) {

    val movieTitle = movie.title

    val imageUrl = Constants.BASE_IMAGE_URL.plus(movie.posterPath)
}