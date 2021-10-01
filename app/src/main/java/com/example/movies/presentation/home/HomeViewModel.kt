package com.example.movies.presentation.home

import androidx.lifecycle.*
import com.example.movies.MyMovie
import com.example.movies.data.remote.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeViewModel(val repository: MovieRepository): ViewModel() {
    private val _uiState = MutableLiveData<UiState>(UiState.Resume)
    val uiState: LiveData<UiState> = _uiState

    private val _movies = MutableLiveData<List<MyMovie>>(emptyList())
    val movies: LiveData<List<MyMovie>> = _movies

    fun getMovies() {
        viewModelScope.launch {
           _uiState.value = UiState.Loading
            repository.getData()
                .catch {

                }
                .onCompletion {
                   _uiState.value = UiState.Resume
                }
                .collect {
                    _movies.value = it.results
                }
        }
    }

    fun searchMovies(query: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.searchMovies(query)
                .catch {

                }
                .onCompletion {
                    _uiState.value = UiState.Resume
                }
                .collect {
                    _movies.value = it.results
                }
        }
    }

    fun getMoviesByCategory(genreId: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.getMoviesByCategory(genreId)
                .catch {

                }
                .onCompletion {
                    _uiState.value = UiState.Resume
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

sealed class Resource<out T: Any?> {
    data class Data<out T: Any?>(val data: T) : Resource<T>()
    object Loading: Resource<Nothing>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}

sealed class UiState {
    object Resume : UiState()
    data class Error(val throwable: Throwable) : UiState()
    object Loading : UiState()
}