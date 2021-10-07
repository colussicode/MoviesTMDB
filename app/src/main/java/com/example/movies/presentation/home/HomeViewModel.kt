package com.example.movies.presentation.home

import androidx.lifecycle.*
import com.example.movies.data.models.MyMovie
import com.example.movies.data.remote.repository.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState = MutableLiveData<UiState>(UiState.Resume)
    val uiState: LiveData<UiState> = _uiState

    private val _movies = MutableLiveData<List<MyMovie>>(emptyList())
    val movies: LiveData<List<MyMovie>> = _movies

    fun getMovies() {
        repository.getData().customCollect {
            _movies.value = it.results
        }
    }

    fun searchMovies(query: String) {
        repository.searchMovies(query).customCollect {
            _movies.value = it.results
        }
    }

    fun getMoviesByCategory(genreId: Int) {
        repository.getMoviesByCategory(genreId).customCollect {
            _movies.value = it.results
        }
    }

    class HomeViewModelFactory(private val repository: MovieRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }

    private fun <T> Flow<T>.customCollect(onSuccess: (T) -> Unit) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            catch { error: Throwable ->
                _uiState.value = UiState.Error(error)
            }
            onCompletion {
                _uiState.value = UiState.Resume
            }
            collect {
                onSuccess(it)
            }
        }
    }
}

sealed class Resource<out T : Any?> {
    data class Data<out T : Any?>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}

sealed class UiState {
    object Resume : UiState()
    data class Error(val throwable: Throwable) : UiState()
    object Loading : UiState()
}