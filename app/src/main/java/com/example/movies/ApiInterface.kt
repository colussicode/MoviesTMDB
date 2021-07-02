package com.example.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/now_playing")
    fun getData(@Query("api_key") apiKey: String): Call<MovieResponse>
}