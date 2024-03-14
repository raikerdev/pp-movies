package com.raikerdev.petproject.movies.ui

import com.raikerdev.petproject.data.PermissionChecker
import com.raikerdev.petproject.data.datasource.LocationDataSource
import com.raikerdev.petproject.movies.data.database.DbMovie
import com.raikerdev.petproject.movies.data.database.MovieDao
import com.raikerdev.petproject.movies.data.server.RemoteMovie
import com.raikerdev.petproject.movies.data.server.RemoteResult
import com.raikerdev.petproject.movies.data.server.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMovieDao(movies: List<DbMovie> = emptyList()): MovieDao {

    private val inMemoryMovies = MutableStateFlow(movies)
    private lateinit var findMovieFlow: MutableStateFlow<DbMovie>

    override fun getAll(): Flow<List<DbMovie>> = inMemoryMovies

    override fun findById(id: Int): Flow<DbMovie> {
        findMovieFlow = MutableStateFlow(inMemoryMovies.value.first { it.id == id })
        return findMovieFlow
    }

    override suspend fun movieCount(): Int = inMemoryMovies.value.size

    override suspend fun insertMovies(movies: List<DbMovie>) {
        inMemoryMovies.value = movies

        if (::findMovieFlow.isInitialized) {
            movies.firstOrNull() { it.id == findMovieFlow.value.id }
                ?.let { findMovieFlow.value = it }
        }
    }

}

class FakeRemoteService(private val movies: List<RemoteMovie> = emptyList()): RemoteService {

    override suspend fun listPopularMovies(apiKey: String, region: String): RemoteResult =
        RemoteResult(1, movies, 1, movies.size)

}

class FakeLocationDataSource: LocationDataSource {

    var location = "US"

    override suspend fun findLastLocation(): String = location

}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}
