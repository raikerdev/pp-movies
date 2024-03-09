package com.raikerdev.petproject.movies.data.datasource

import com.raikerdev.petproject.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): List<Movie>
}
