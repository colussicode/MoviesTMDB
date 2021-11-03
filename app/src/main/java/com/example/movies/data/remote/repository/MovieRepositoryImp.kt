package com.example.movies.data.remote.repository

import com.example.movies.data.remote.service.MovieService
import com.example.movies.data.local.MovieDao
import com.example.movies.data.remote.RetrofitService

class MovieRepositoryImp(private val movieDao: MovieDao) : MovieRepository {
    private val service = RetrofitService.retrofitInstance.create(MovieService::class.java)

    override suspend fun getData() = service.getData().results

    override suspend fun searchMovies(query: String) = service.searchMovies(query).results

    override suspend fun getMovieDetails(movieId: Int) = service.getMovieDetails(movieId)

    override suspend fun getMoviesByCategory(genreId: Int) = service.getMoviesByCategory(genreId).results

}