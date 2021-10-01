package com.example.movies.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movies.MovieService
import com.example.movies.MyMovie
import com.example.movies.data.models.FavouriteMovieEntity
import com.example.movies.data.models.MovieSearchEntity
import com.example.movies.data.RoomSearchDataBase
import com.example.movies.data.models.MovieGenre
import com.example.movies.data.remote.retrofitBuilder
import com.example.movies.databinding.GetMoviesBinding
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.presentation.details.DetailsActivity
import com.example.movies.presentation.favourite.FavouriteActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.themoviedb.org/3/"

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: GetMoviesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GetMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabLayout()
        setListeners()
    }

    private fun setView () = binding.run {

    }

    private fun setTabLayout() = binding.run {
        val adapter = ViewPageAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            MovieGenre.values().find {
                it.ordinal == position
            }?.let {
                tab.text = it.name
            }
        }.attach()
    }

    private fun setListeners () = binding.run {
        favouriteButton.setOnClickListener { goToFavouriteMovies() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val searchItem = menu?.findItem(R.id.nvar_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    RoomSearchDataBase.getInstance(this@HomeActivity).movieDao().insertSearch(
                        MovieSearchEntity(
                            query.toString()
                        )
                    )
                }
                val retrofitInstance by retrofitBuilder<MovieService>()
                retrofitInstance.

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
                            val movieAdapter = MovieListAdapter(it.results, this@HomeActivity)
                            filteredRecyclerView = findViewById(R.id.action_category_movies_list)
                            filteredRecyclerView.adapter = movieAdapter
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
                   val movies =  RoomSearchDataBase.getInstance(this@HomeActivity).movieDao()
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


    private fun goToFavouriteMovies() {
        val context = baseContext
        val intent = Intent(context, FavouriteActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun onClickFavourite(myMovie: MyMovie) {
        if (isFavourite) {
            CoroutineScope(Dispatchers.IO).launch {
                    RoomSearchDataBase.getInstance(this@HomeActivity).movieDao()
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
                    RoomSearchDataBase.getInstance(this@HomeActivity).movieDao().insertMovieId(
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

    private fun showDetails(id: Int) {
        val context = baseContext

        val intent = Intent(context, DetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("id", id)
        context.startActivity(intent)
    }
}