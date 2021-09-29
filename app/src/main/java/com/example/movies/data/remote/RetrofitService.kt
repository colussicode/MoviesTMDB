package com.example.movies.data.remote

import android.util.Log
import com.example.movies.presentation.home.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.ReadOnlyProperty

inline fun <reified T> retrofitBuilder (vararg args: Interceptor) = ReadOnlyProperty<Any?, T> {_, _ ->
    val okHttp = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            Log.i("OKHTTP", chain.request().url.toString())
            val newUrl = chain.request().url.newBuilder().addQueryParameter("api_key", "39fd0a08c0cc7fd3041fc14605c22358").build()
            chain.proceed(chain.request().newBuilder().url(newUrl).build())
        }
    }
    args.forEach {
        okHttp.addInterceptor(it)
    }

    val retrofitInstance = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttp.build())
    retrofitInstance.build().create(T:: class.java)


}