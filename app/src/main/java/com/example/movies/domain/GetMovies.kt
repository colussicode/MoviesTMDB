package com.example.movies.domain

import com.example.movies.data.MoviesRepository
import com.example.movies.domain.model.Movie

class GetMoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository
) : GetMoviesUseCase {

    override suspend fun invoke(): List<Movie> = try {
        moviesRepository.getMovies()
    } catch (e: Exception) {
        listOf()
    }
}

interface GetMoviesUseCase {
    suspend operator fun invoke() : List<Movie>
}