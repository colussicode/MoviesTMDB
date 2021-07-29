package com.example.movies

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.adapter.MovieListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.themoviedb.org/3/"
class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var editText : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_movies)

        recyclerView = findViewById(R.id.rv_movies_list)
        editText = findViewById(R.id.filter_movies)

        val searchButton : Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener { getInputText() }
        getMyData()
    }

    private fun getMyData () {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(getData::class.java)

        val retrofitData = retrofitBuilder.getData("39fd0a08c0cc7fd3041fc14605c22358")
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                response.body()?.let {
                    val movieAdapter = MovieListAdapter(it.results)
                    recyclerView.adapter = movieAdapter
                    movieAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getInputText () {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(getData::class.java)

        val retrofitData = retrofitBuilder.searchMovies("39fd0a08c0cc7fd3041fc14605c22358", editText.text.toString())
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                response.body()?.let {
                    val movieAdapter = MovieListAdapter(it.results)
                    recyclerView.adapter = movieAdapter
                    movieAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
