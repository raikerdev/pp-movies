package com.raikerdev.petproject.movies.data.datasource

interface LocationDataSource {
    suspend fun findLastLocation(): String?
}


