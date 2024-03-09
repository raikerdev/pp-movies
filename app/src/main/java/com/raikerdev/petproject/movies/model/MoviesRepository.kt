package com.raikerdev.petproject.movies.model

import com.raikerdev.petproject.movies.App
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.model.database.Movie
import com.raikerdev.petproject.movies.model.datasource.MovieLocalDataSource
import com.raikerdev.petproject.movies.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow


class MoviesRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key)
    )

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies.results.toLocalModel())
        }
    }

    suspend fun switchFavorite(movie: Movie): Error? = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }

}

private fun List<RemoteMovie>.toLocalModel(): List<Movie> = map {
    with(it) {
        Movie(
            id,
            title,
            overview,
            releaseDate,
            posterPath,
            backdropPath ?: "",
            originalLanguage,
            originalTitle,
            popularity,
            voteAverage,
            false
        )
    }
}

