package com.raikerdev.petproject.data

import com.raikerdev.petproject.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.domain.Error
import com.raikerdev.petproject.domain.Movie

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
) {

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            movies.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(movie: Movie): Error? {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        return localDataSource.save(listOf(updatedMovie))
    }

}
