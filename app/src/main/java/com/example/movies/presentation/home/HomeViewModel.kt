package com.example.movies.presentation.home

import androidx.lifecycle.*
import com.example.movies.MyMovie
import com.example.movies.data.remote.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeViewModel(val repository: MovieRepository): ViewModel() {
    private val _movies = MutableLiveData<List<MyMovie>>(emptyList())
    val movies: LiveData<List<MyMovie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Throwable>(null)
    val isError: LiveData<Throwable> = _isError

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getData()
                .catch {
                    _isError.value = it
                }
                .onCompletion {
                    _isLoading.value = false
                }
                .collect {
                    _movies.value = it.results
                }
        }
    }

    fun searchMovies(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.searchMovies(query)
                .catch {
                    _isError.value = it
                }
                .onCompletion {
                    _isLoading.value = false
                }
                .collect {
                    _movies.value = it.results
                }
        }
    }

    fun getMoviesByCategory(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getMoviesByCategory(movieId)
                .catch {
                    _isError.value = it
                }
                .onCompletion {
                    _isLoading.value = false
                }
                .collect {
                    _movies.value = it.results
                }
        }
    }

    class HomeViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}