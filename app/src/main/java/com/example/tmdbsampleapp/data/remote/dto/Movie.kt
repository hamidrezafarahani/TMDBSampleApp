package com.example.tmdbsampleapp.data.remote.dto


import com.google.gson.annotations.SerializedName as SN

data class Movie(
    @SN("adult") val adult: Boolean,
    @SN("backdrop_path") val backdropPath: String,
    @SN("genre_ids") val genreIds: List<Int>,
    @SN("id") val id: Int,
    @SN("original_language") val originalLanguage: String,
    @SN("original_title") val originalTitle: String,
    @SN("overview") val overview: String,
    @SN("popularity") val popularity: Double,
    @SN("poster_path") val posterPath: String,
    @SN("release_date") val releaseDate: String,
    @SN("title") val title: String,
    @SN("video") val video: Boolean,
    @SN("vote_average") val voteAverage: Double,
    @SN("vote_count") val voteCount: Int
)