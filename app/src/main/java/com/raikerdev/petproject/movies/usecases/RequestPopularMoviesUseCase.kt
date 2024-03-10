package com.raikerdev.petproject.movies.usecases

import com.raikerdev.petproject.movies.domain.Error
import com.raikerdev.petproject.movies.data.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? = moviesRepository.requestPopularMovies()

}
