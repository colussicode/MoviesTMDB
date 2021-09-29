package com.example.movies

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getData(): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") movieName : String): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId : Int) : MovieDetails

    @GET("discover/movie")
    suspend fun getMoviesByCategory(@Query("with_genres") genreId : Int) : MovieResponse
}
