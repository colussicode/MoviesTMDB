package com.example.movies


data class MyMovie (
    val id: Int,

    val title: String?,

    val release_date: String?,

    val vote_average: String?,

    val poster_path: String?,

    var isFavourite: Boolean = false
)

data class MovieDetails(
    val id: Int?,

    val title: String?,

    val status : String?,

    val release_date: String?,

    val tagline: String?,

    val poster_path: String?
)

data class MovieResponse (val dates: MovieDates, val page: Int, val results: List<MyMovie>)

data class MovieDates (val minimum: String, val maximum: String)