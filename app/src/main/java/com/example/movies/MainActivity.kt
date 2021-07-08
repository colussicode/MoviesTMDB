package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.themoviedb.org/3/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMyData()
    }

    private fun getMyData () {
        //API log
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("39fd0a08c0cc7fd3041fc14605c22358")
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                val responseBody = response.body()
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
