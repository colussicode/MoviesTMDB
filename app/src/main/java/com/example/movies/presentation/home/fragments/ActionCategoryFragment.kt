package com.example.movies.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.*
import com.example.movies.presentation.home.MovieListAdapter
import com.example.movies.data.models.FavouriteMovieEntity
import com.example.movies.data.RoomSearchDataBase
import com.example.movies.databinding.FragmentActionCategoryBinding
import com.example.movies.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActionCategoryFragment : Fragment() {

    private lateinit var binding: FragmentActionCategoryBinding
    private val viewModel: HomeViewModel by activityViewModels()

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter(object: MovieListAdapter.FavouriteMovieListener {
            override fun onClickFavourite(myMovie: MyMovie) {
                this@ActionCategoryFragment.onClickFavourite(myMovie)
            }

            override fun showDetails(id: Int) {
                this@ActionCategoryFragment.showDetails(id)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActionCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        getActionMovies()
    }

    private fun setView() = binding.run {
        rvMovies.adapter = adapter
    }

    private fun setObservable() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun getActionMovies() {

        lateinit var favouriteMovies: List<FavouriteMovieEntity>
        CoroutineScope(Dispatchers.IO).launch {
            favouriteMovies =
                context?.let { RoomSearchDataBase.getInstance(it).movieDao().getFavouriteMovies() }!!
        }


        val retrofitData = retrofitBuilder.getMoviesByCategory("39fd0a08c0cc7fd3041fc14605c22358", 28)
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

                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun onClickFavourite(myMovie: MyMovie) {
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