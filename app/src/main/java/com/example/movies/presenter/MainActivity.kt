package com.example.movies.presenter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.adapter.MovieListAdapter
import com.example.movies.domain.model.Movie

class MainActivity : AppCompatActivity(){

    lateinit var filteredRecyclerView: RecyclerView
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filteredRecyclerView = findViewById(R.id.filtered_list)

        moviesViewModel._movies.observe(this, { movieList ->
            populateMoviesAdapter(movieList)
        })

        moviesViewModel.getMovies()
    }

    private fun populateMoviesAdapter(movies: List<Movie>) {
        filteredRecyclerView.adapter = MovieListAdapter(movies)
    }
}