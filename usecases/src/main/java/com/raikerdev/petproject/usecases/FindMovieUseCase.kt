package com.raikerdev.petproject.usecases

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.domain.Movie
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<Movie> = repository.findById(id)

}
