package com.example.tmdbsampleapp.ui.movies

import android.view.View
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.shared.Constants.BASE_IMAGE_URL
import com.example.tmdbsampleapp.utils.log

class ItemMovieViewModel(private val movie: Movie) {

    val movieTitle = movie.title
    val imageUrl = BASE_IMAGE_URL.plus(movie.posterPath)

    fun onClick(v: View) {
        // do something with movie for example:
        v.context.applicationContext log movie.title
    }
}