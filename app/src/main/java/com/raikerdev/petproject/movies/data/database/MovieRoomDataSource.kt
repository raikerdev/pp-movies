package com.raikerdev.petproject.movies.data.database

import com.raikerdev.petproject.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.domain.Error
import com.raikerdev.petproject.domain.Movie
import com.raikerdev.petproject.movies.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {
    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        movieDao.insertMovies(movies.fromDomainModel())
    }.fold (
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

private fun List<Movie>.fromDomainModel(): List<DbMovie> = map {
    with(it) {
        DbMovie(
            id,
            title,
            overview,
            releaseDate,
            posterPath,
            backdropPath,
            originalLanguage,
            originalTitle,
            popularity,
            voteAverage,
            favorite
        )
    }
}
