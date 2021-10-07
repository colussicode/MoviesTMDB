package com.example.movies.data.remote.repository

import com.example.movies.data.models.MovieResponse
import com.example.movies.data.models.MyMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getData(): Flow<MovieResponse>

    fun searchMovies(query : String): Flow<MovieResponse>

    fun getMovieDetails(movieId : Int) : Flow<MyMovie>

    fun getMoviesByCategory(genreId : Int) : Flow<MovieResponse>
}