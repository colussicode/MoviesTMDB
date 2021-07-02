package com.example.movies

data class MyDataItem (
    val title: String,
    val release_date: String,
    val vote_average: String
)

data class MovieResponse (val dates: MovieDates, val page: Int, val results: List<MyDataItem>)

data class MovieDates (val minimum: String, val maximum: String)