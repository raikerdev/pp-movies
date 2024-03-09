package com.raikerdev.petproject.movies.model

import com.raikerdev.petproject.movies.App
import com.raikerdev.petproject.movies.R
import com.raikerdev.petproject.movies.model.database.Movie
import com.raikerdev.petproject.movies.model.datasource.MovieLocalDataSource
import com.raikerdev.petproject.movies.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key),
        RegionRepository(application)
    )

    val popularMovies = localDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
            localDataSource.save(movies.results.toLocalModel())
        }
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
            voteAverage
        )
    }
}

