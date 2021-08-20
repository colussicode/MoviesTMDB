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
import com.example.movies.data.FavouriteMovieEntity
import com.example.movies.data.MovieSearchEntity
import com.example.movies.data.RoomSearchDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        getMyData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val searchItem = menu?.findItem(R.id.nvar_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    RoomSearchDataBase.getInstance(this@MainActivity).movieDao().insertSearch(
                        MovieSearchEntity(
                            0, query
                        )
                    )
                }

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
                    CoroutineScope(Dispatchers.Default).launch {
                        it.results.forEach {
                            RoomSearchDataBase.getInstance(this@MainActivity).movieDao().getFavouriteMovies()
                            it.isFavourite = true
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
        if (!isFavourite) {
            CoroutineScope(Dispatchers.IO).launch {
                RoomSearchDataBase.getInstance(this@MainActivity).movieDao()
                    .insertMovieId(FavouriteMovieEntity(movieId))
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                RoomSearchDataBase.getInstance(this@MainActivity).movieDao().deleteMovieId(
                    FavouriteMovieEntity(movieId)
                )
            }
        }
    }
}
