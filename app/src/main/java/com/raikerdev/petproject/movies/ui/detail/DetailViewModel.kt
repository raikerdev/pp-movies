package com.raikerdev.petproject.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raikerdev.petproject.usecases.FindMovieUseCase
import com.raikerdev.petproject.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailViewModel(
    movieId: Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId).collect{
                _state.value = UiState(it)
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch{
            _state.value.movie?.let { switchMovieFavoriteUseCase(it) }
        }
    }

    data class UiState(val movie: com.raikerdev.petproject.domain.Movie? = null)

}
