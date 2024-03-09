package com.raikerdev.petproject.movies.usecases

import com.raikerdev.petproject.movies.data.Error
import com.raikerdev.petproject.movies.data.MoviesRepository
import com.raikerdev.petproject.movies.domain.Movie

class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = repository.switchFavorite(movie)

}
