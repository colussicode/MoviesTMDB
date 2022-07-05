package com.example.movies.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.GetMoviesUseCase
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel(){

    val _movies = MutableLiveData<List<Movie>>()

    fun getMovies(){
        viewModelScope.launch {
            val movieList = getMoviesUseCase()

            _movies.value = movieList
            }
        }
    }