package com.raikerdev.petproject.movies.framework.datasource

import com.raikerdev.petproject.movies.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.movies.framework.database.DbMovie
import com.raikerdev.petproject.movies.framework.database.MovieDao
import com.raikerdev.petproject.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {
    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies.fromDomainModel())
    }
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie = Movie(
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
