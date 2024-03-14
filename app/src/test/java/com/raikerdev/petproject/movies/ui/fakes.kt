package com.raikerdev.petproject.movies.ui

import arrow.core.Either
import arrow.core.right
import com.raikerdev.petproject.data.PermissionChecker
import com.raikerdev.petproject.data.datasource.LocationDataSource
import com.raikerdev.petproject.data.datasource.MovieLocalDataSource
import com.raikerdev.petproject.data.datasource.MovieRemoteDataSource
import com.raikerdev.petproject.domain.Error
import com.raikerdev.petproject.domain.Movie
import com.raikerdev.petproject.domain.test.sampleMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

val defaultFakeMovies = listOf(
    sampleMovie.copy(id = 1),
    sampleMovie.copy(id = 2),
    sampleMovie.copy(id = 3),
    sampleMovie.copy(id = 4)
)

class FakeLocalDataSource: MovieLocalDataSource {

    val inMemoryMovies = MutableStateFlow<List<Movie>>(emptyList())

    override val movies = inMemoryMovies

    private lateinit var findMovieFlow: MutableStateFlow<Movie>

    override suspend fun isEmpty(): Boolean = movies.value.isEmpty()

    override fun findById(id: Int): Flow<Movie> {
        findMovieFlow = MutableStateFlow(inMemoryMovies.value.first{ it.id == id })
        return findMovieFlow
    }

    override suspend fun save(movies: List<Movie>): Error? {
        inMemoryMovies.value = movies
        if(::findMovieFlow.isInitialized) {
            movies.firstOrNull() { it.id == findMovieFlow.value.id }?.let {
                findMovieFlow.value = it
            }
        }
        return null
    }

}

class FakeRemoteDataSource: MovieRemoteDataSource {

    var movies = defaultFakeMovies

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = movies.right()

}

class FakeLocationDataSource: LocationDataSource {

    var location = "US"

    override suspend fun findLastLocation(): String = location

}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}
