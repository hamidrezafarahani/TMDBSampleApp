package com.example.tmdbsampleapp.ui.movies

import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.shared.Constants.BASE_IMAGE_URL

class ItemMovieViewModel(private val movie: Movie) {

    val movieTitle = movie.title

    val imageUrl = BASE_IMAGE_URL.plus(movie.posterPath)
}