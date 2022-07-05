package com.example.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    fun getData(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("search/movie")
    fun searchMovies(@Query("api_key") apiKey: String, @Query("query") movieName : String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId : Int, @Query("api_key") apiKey: String) : Call<MovieDetails>

    @GET("discover/movie")
    fun getMoviesByCategory(@Query("api_key") apiKey : String, @Query("with_genres") genreId : Int) : Call<MovieResponse>
}
