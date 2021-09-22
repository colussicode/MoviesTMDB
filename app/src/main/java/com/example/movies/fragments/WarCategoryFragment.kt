package com.example.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.BASE_URL
import com.example.movies.MovieResponse
import com.example.movies.MoviesData
import com.example.movies.R
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

class WarCategoryFragment : Fragment(R.layout.fragment_war_category),
    MovieListAdapter.FavouriteMovieListener {

    lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDramaMovies()
    }

    private fun getDramaMovies() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesData::class.java)

        lateinit var favouriteMovies: List<FavouriteMovieEntity>
        CoroutineScope(Dispatchers.IO).launch {
            favouriteMovies =
                context?.let {
                    RoomSearchDataBase.getInstance(it).movieDao().getFavouriteMovies()
                }!!
        }


        val retrofitData =
            retrofitBuilder.getMoviesByCategory("39fd0a08c0cc7fd3041fc14605c22358", 10752)
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

                    recyclerView = requireView().findViewById(R.id.war_category_movies_list)
                    val movieAdapter = MovieListAdapter(it.results, this@WarCategoryFragment)
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