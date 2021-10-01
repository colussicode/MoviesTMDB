package com.example.movies.data.remote.repository

import com.example.movies.MovieResponse
import com.example.movies.MovieService
import com.example.movies.MyMovie
import com.example.movies.data.remote.retrofitBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImp() : MovieRepository{
    private val service: MovieService by retrofitBuilder()

    override fun getData(): Flow<MovieResponse> = flow {
        emit(service.getData())
    }

    override fun searchMovies(query: String): Flow<MovieResponse> = flow {
        emit(service.searchMovies(query))
    }

    override fun getMovieDetails(movieId: Int): Flow<MyMovie> = flow {
        emit(service.getMovieDetails(movieId))
    }

    override fun getMoviesByCategory(genreId: Int): Flow<MovieResponse> = flow {
        emit(service.getMoviesByCategory(genreId))
    }

}