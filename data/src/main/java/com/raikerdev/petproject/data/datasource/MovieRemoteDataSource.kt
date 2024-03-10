package com.raikerdev.petproject.data.datasource

import arrow.core.Either
import com.raikerdev.petproject.domain.Error
import com.raikerdev.petproject.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}
