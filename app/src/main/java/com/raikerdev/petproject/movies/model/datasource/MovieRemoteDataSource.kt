package com.raikerdev.petproject.movies.model.datasource

import com.raikerdev.petproject.movies.model.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)

}
