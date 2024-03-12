package com.raikerdev.petproject.usecases

import com.raikerdev.petproject.data.MoviesRepository
import com.raikerdev.petproject.domain.Movie
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke(): Flow<List<Movie>> = repository.popularMovies

}
