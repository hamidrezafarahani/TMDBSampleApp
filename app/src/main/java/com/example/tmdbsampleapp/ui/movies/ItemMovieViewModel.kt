package com.example.tmdbsampleapp.ui.movies

import android.view.View
import com.example.tmdbsampleapp.data.remote.dto.Movie
import com.example.tmdbsampleapp.utils.log

class ItemMovieViewModel(private val movie: Movie) {

    fun onClick(v: View) {
        // do something with movie for example:
        v.context.applicationContext log movie.title
    }
}