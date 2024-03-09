package com.raikerdev.petproject.movies.domain

import com.raikerdev.petproject.movies.data.MoviesRepository
import com.raikerdev.petproject.movies.data.database.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    operator fun invoke(): Flow<List<Movie>> = repository.popularMovies

}
