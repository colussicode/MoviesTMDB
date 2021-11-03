package com.example.movies.data.models

data class MyMovie (
    val id: Int,

    val title: String?,

    val release_date: String?,

    val vote_average: String?,

    val poster_path: String?,

    var isFavourite: Boolean = false,

    val tagline: String?,

    val status : String?
)

data class MovieResponse (val dates: MovieDates, val page: Int, val results: List<MyMovie>)

data class MovieDates (val minimum: String, val maximum: String)