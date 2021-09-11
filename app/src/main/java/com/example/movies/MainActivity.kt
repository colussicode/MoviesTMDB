package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.adapter.FilteredListAdapter
import com.example.movies.adapter.MovieListAdapter
import com.example.movies.data.FavouriteMovieEntity
import com.example.movies.data.MovieSearchEntity
import com.example.movies.data.RoomSearchDataBase
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.themoviedb.org/3/"

class MainActivity : AppCompatActivity(), MovieListAdapter.FavouriteMovieListener {
    lateinit var recyclerView: RecyclerView
    lateinit var filteredRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_movies)
        recyclerView = findViewById(R.id.rv_movies_list)
        val goToFavouriteIntentButton: Button = findViewById(R.id.go_to_favourite_movies)
        goToFavouriteIntentButton.setOnClickListener { goToFavouriteMovies() }

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
                            query.toString()
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

            override fun onQueryTextChange(newText: String): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                   val movies =  RoomSearchDataBase.getInstance(this@MainActivity).movieDao()
                        .getSearch(newText)

                    withContext(Dispatchers.Main){
                            filteredRecyclerView = findViewById(R.id.filtered_list)
                            val filteredListAdapter = FilteredListAdapter(movies)
                            filteredRecyclerView.adapter = filteredListAdapter
                            filteredRecyclerView.visibility = View.VISIBLE
                    }
                    if (newText.isEmpty()) filteredRecyclerView.visibility = View.GONE
                }

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

        lateinit var favouriteMovies: List<FavouriteMovieEntity>
        CoroutineScope(Dispatchers.IO).launch {
            favouriteMovies =
                RoomSearchDataBase.getInstance(this@MainActivity).movieDao().getFavouriteMovies()
        }


        val retrofitData = retrofitBuilder.getData("39fd0a08c0cc7fd3041fc14605c22358")
        retrofitData.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                response.body()?.let {
                    it.results.forEach { movie ->
                        val fMovie = favouriteMovies.find { favouriteMovie ->
                            favouriteMovie.id == movie.id
                        }
                        movie.isFavourite = fMovie != null
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

    override fun onClickFavourite(
        movieId: Int,
        movieTitle: String,
        movieReleaseDate: String,
        movieVoteAverage: String,
        moviePosterPath: String,
        isFavourite: Boolean
    ) {
        if (isFavourite) {
            CoroutineScope(Dispatchers.IO).launch {
                RoomSearchDataBase.getInstance(this@MainActivity).movieDao()
                    .deleteMovieId(
                        FavouriteMovieEntity(
                            movieId,
                            movieTitle,
                            movieReleaseDate,
                            movieVoteAverage,
                            moviePosterPath,
                            isFavourite
                        )
                    )
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                RoomSearchDataBase.getInstance(this@MainActivity).movieDao().insertMovieId(
                    FavouriteMovieEntity(
                        movieId,
                        movieTitle,
                        movieReleaseDate,
                        movieVoteAverage,
                        moviePosterPath,
                        isFavourite
                    )
                )
            }
        }
    }

    private fun goToFavouriteMovies() {
        val context = baseContext
        val intent = Intent(context, FavouriteActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}