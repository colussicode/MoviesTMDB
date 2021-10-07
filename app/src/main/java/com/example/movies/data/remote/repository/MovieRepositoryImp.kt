package com.example.movies.data.remote.repository

import com.example.movies.data.models.MovieResponse
import com.example.movies.data.remote.service.MovieService
import com.example.movies.data.models.MyMovie
import com.example.movies.data.local.MovieDao
import com.example.movies.data.remote.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImp(private val movieDao: MovieDao) : MovieRepository{
    private val service = RetrofitService.retrofitInstance.create(MovieService::class.java)

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