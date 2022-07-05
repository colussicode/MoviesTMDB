package com.example.movies.data.model

import com.example.movies.domain.model.Movie

data class MovieResponse(
    val movieId : Int,
    val movieTitle: String,
    val movieReleaseDate: String,
    val movieVoteAverage: Float,
    val posterPath: String
)

fun MovieResponse.toMovie() = Movie(
    id = this.movieId,
    title = this.movieTitle,
    releaseDate = this.movieReleaseDate,
    voteAverage = this.movieVoteAverage,
    posterPath = this.posterPath
)
