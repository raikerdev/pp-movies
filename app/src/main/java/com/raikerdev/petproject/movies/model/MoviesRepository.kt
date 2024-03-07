package com.raikerdev.petproject.movies.model

import android.app.Application
import com.raikerdev.petproject.movies.R

class MoviesRepository(application: Application) {
    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies() =
        RemoteConnection.service.listPopularMovies(apiKey, regionRepository.findLastRegion())

}