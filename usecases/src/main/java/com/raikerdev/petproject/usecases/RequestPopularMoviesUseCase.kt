package com.raikerdev.petproject.usecases

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.domain.Error
import org.koin.core.annotation.Factory

@Factory
class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? = moviesRepository.requestPopularMovies()

}
