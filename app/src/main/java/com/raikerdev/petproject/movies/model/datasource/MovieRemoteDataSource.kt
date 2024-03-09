package com.raikerdev.petproject.movies.model.datasource

import com.raikerdev.petproject.movies.model.RegionRepository
import com.raikerdev.petproject.movies.model.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String, private val regionRepository: RegionRepository) {

    suspend fun findPopularMovies() =
        RemoteConnection.service.listPopularMovies(apiKey, regionRepository.findLastRegion())

}
