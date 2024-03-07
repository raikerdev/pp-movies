package com.raikerdev.petproject.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raikerdev.petproject.movies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(movie: Movie): ViewModel() {

    private val _state = MutableStateFlow(UiState(movie))
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    data class UiState(val movie: Movie)

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movie) as T
    }
}