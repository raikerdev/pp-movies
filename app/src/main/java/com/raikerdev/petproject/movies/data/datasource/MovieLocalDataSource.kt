package com.raikerdev.petproject.movies.data.datasource

import com.raikerdev.petproject.movies.data.database.Movie
import com.raikerdev.petproject.movies.data.database.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDao: MovieDao) {
    val movies: Flow<List<Movie>> = movieDao.getAll()

    suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    fun findById(id: Int): Flow<Movie> = movieDao.findById(id)

    suspend fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }
}
