package com.example.movies

import com.example.movies.domain.model.Movie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getAllMovies(@Query("api_key") apiKey: String): Response<List<Movie>>?

    @GET("search/movie")
    fun searchMovies(@Query("api_key") apiKey: String, @Query("query") movieName : String): Response<Movie>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId : Int, @Query("api_key") apiKey: String) : Response<Movie>

    @GET("discover/movie")
    fun getMoviesByCategory(@Query("api_key") apiKey : String, @Query("with_genres") genreId : Int) : Response<Movie>
}
