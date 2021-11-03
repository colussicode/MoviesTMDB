package com.example.movies.data.remote

import android.util.Log
import com.example.movies.presentation.home.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.ReadOnlyProperty

object RetrofitService {
    val retrofitInstance: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                Log.i("OKHTTP", chain.request().url.toString())
                val newUrl = chain.request().url.newBuilder().addQueryParameter("api_key", "39fd0a08c0cc7fd3041fc14605c22358").build()
                chain.proceed(chain.request().newBuilder().url(newUrl).build())
            }
        }.build()).build()
}