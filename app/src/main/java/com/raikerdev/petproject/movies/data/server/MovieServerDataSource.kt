package com.raikerdev.petproject.movies.data.server

import arrow.core.Either
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.domain.Error
import com.raikerdev.petproject.domain.Movie
import com.raikerdev.petproject.movies.data.tryCall
import com.raikerdev.petproject.movies.di.ApiKey
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        remoteService.listPopularMovies(apiKey, region).results.toDomainModel()
    }

}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )
