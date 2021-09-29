package com.example.movies.data.remote.repository

import com.example.movies.MovieResponse
import com.example.movies.MyMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getData(): Flow<MovieResponse>

    fun searchMovies(query : String): Flow<MovieResponse>

    fun getMovieDetails(movieId : Int) : Flow<MyMovie>

    fun getMoviesByCategory(genreId : Int) : Flow<MovieResponse>
}