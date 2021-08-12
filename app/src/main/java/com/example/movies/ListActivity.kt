package com.example.movies

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.movies.adapter.MovieListAdapter
import com.example.movies.data.Database
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.themoviedb.org/3/"

class MainActivity : AppCompatActivity(), MovieListAdapter.FavouriteMovieListener {
    lateinit var recyclerView: RecyclerView
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_movies)
        recyclerView = findViewById(R.id.rv_movies_list)

        sharedPref = getSharedPreferences("ids", Context.MODE_PRIVATE)
        getMyData()

        val db = Room.databaseBuilder(
            baseContext,
            Database::class.java, "movies-database"
        ).build()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val searchItem = menu?.findItem(R.id.nvar_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(moviesData::class.java)

                val retrofitData = retrofitBuilder.searchMovies(
                    "39fd0a08c0cc7fd3041fc14605c22358",
                    query.toString()
                )
                retrofitData.enqueue(object : Callback<MovieResponse?> {
                    override fun onResponse(
                        call: Call<MovieResponse?>,
                        response: Response<MovieResponse?>
                    ) {
                        response.body()?.let {
                            val movieAdapter = MovieListAdapter(it.results, this@MainActivity)
                            recyclerView.adapter = movieAdapter
                            movieAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return true
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(moviesData::class.java)

        val retrofitData = retrofitBuilder.getData("39fd0a08c0cc7fd3041fc14605c22358")
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                response.body()?.let {
                    it.results.forEach {
                        sharedPref.run {
                            var movieId = getInt(it.id.toString(), -1)
                            if (movieId != -1) {
                                it.isFavourite = true
                            }
                        }
                    }
                    val movieAdapter = MovieListAdapter(it.results, this@MainActivity)
                    recyclerView.adapter = movieAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onClickFavourite(movieId: Int, isFavourite: Boolean) {
        val editor = sharedPref.edit()
        if (!isFavourite) {
            editor.apply {
                this.putInt(movieId.toString(), movieId)
                apply()
            }
        } else {
            editor.remove(movieId.toString())
            editor.apply()
        }

    }
}
