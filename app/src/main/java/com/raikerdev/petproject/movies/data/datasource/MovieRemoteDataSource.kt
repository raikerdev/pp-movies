package com.raikerdev.petproject.movies.data.datasource

import com.raikerdev.petproject.movies.data.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)

}
