package com.raikerdev.petproject.data

import com.raikerdev.petproject.data.datasource.LocationDataSource
import org.koin.core.annotation.Factory

@Factory
class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
           locationDataSource.findLastLocation() ?: DEFAULT_REGION
        }else {
            DEFAULT_REGION
        }
    }

}
