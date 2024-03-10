package com.raikerdev.petproject.movies.data

import com.raikerdev.petproject.movies.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.movies.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.movies.domain.Movie
import kotlinx.coroutines.flow.Flow


class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
) {

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies)
        }
    }

    suspend fun switchFavorite(movie: Movie): Error? = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }

}
