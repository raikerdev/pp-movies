package com.raikerdev.petproject.movies.model

import android.Manifest
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.raikerdev.petproject.movies.ui.common.getFromLocationCompat

class RegionRepository(activity: AppCompatActivity) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSource = PlayServicesLocationDataSource(activity)
    private val coarsePermissionChecker = PermissionChecker(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
    private val geocoder = Geocoder(activity)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? =
        if (coarsePermissionChecker.request()) locationDataSource.findLastLocation()
        else null

    private suspend fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocationCompat(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}