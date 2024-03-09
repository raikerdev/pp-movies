package com.raikerdev.petproject.movies.data.datasource

import com.raikerdev.petproject.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<Movie>

    suspend fun save(movies: List<Movie>)
}
