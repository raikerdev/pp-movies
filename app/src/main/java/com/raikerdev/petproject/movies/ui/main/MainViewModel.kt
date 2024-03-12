package com.raikerdev.petproject.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raikerdev.petproject.movies.data.toError
import com.raikerdev.petproject.usecases.GetPopularMoviesUseCase
import com.raikerdev.petproject.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { UiState(movies = movies) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            val error = requestPopularMoviesUseCase()
            _state.update { it.copy(error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<com.raikerdev.petproject.domain.Movie>? = null,
        val error: com.raikerdev.petproject.domain.Error? = null
    )
}
