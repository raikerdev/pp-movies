package com.raikerdev.petproject.data.datasource

interface LocationDataSource {
    suspend fun findLastLocation(): String?
}
