package com.example.movies.data

import com.example.movies.MovieService
import com.example.movies.domain.model.Movie

class MoviesRepositoryImpl(
    private val service: MovieService
) : MoviesRepository{

    override suspend fun getMovies(): List<Movie> {

        return when(val movieResponseList = service.getAllMovies("39fd0a08c0cc7fd3041fc14605c22358")?.body()) {
            null -> emptyList()
            else -> {movieResponseList}
        }

    }

}

interface MoviesRepository {
    suspend fun getMovies() : List<Movie>
}