package com.raikerdev.petproject.usecases

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.domain.Error
import com.raikerdev.petproject.domain.Movie
import org.koin.core.annotation.Factory

@Factory
class SwitchMovieFavoriteUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = repository.switchFavorite(movie)

}
