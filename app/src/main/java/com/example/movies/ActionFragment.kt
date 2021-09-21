package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.adapter.MovieListAdapter
import com.example.movies.data.FavouriteMovieEntity
import com.example.movies.data.RoomSearchDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActionFragment : Fragment(R.layout.fragment_movie_list), MovieListAdapter.FavouriteMovieListener {
    lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMyData()
    }

    fun newInstance(param1: String) =
        ActionFragment().fragment

    private fun getMyData() {
        recyclerView = requireView().findViewById(R.id.rv_movies_list)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesData::class.java)

        lateinit var favouriteMovies: List<FavouriteMovieEntity>
        CoroutineScope(Dispatchers.IO).launch {
            favouriteMovies =
                context?.let { RoomSearchDataBase.getInstance(it).movieDao().getFavouriteMovies() }!!
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

                    val movieAdapter = MovieListAdapter(it.results, this@ActionFragment)
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
                context?.let {
                    RoomSearchDataBase.getInstance(it).movieDao()
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
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                context?.let {
                    RoomSearchDataBase.getInstance(it).movieDao().insertMovieId(
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
    }
}