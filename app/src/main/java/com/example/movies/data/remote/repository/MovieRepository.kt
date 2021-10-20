package com.example.movies.data.remote.repository

import com.example.movies.data.models.MovieResponse
import com.example.movies.data.models.MyMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getData(): List<MyMovie>

    suspend fun searchMovies(query : String): List<MyMovie>

    suspend fun getMovieDetails(movieId : Int) : MyMovie

    suspend fun getMoviesByCategory(genreId : Int) : List<MyMovie>
}