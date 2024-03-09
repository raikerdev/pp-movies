package com.raikerdev.petproject.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raikerdev.petproject.movies.model.MoviesRepository
import com.raikerdev.petproject.movies.model.database.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    movieId: Int,
    private val repository: MoviesRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.findById(movieId).collect{
                _state.value = UiState(it)
            }
        }
    }

    data class UiState(val movie: Movie? = null)

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val movieId: Int,
    private val repository: MoviesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movieId, repository) as T
    }
}
