package com.raikerdev.petproject.movies.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.raikerdev.petproject.data.datasource.LocationDataSource
import com.raikerdev.petproject.movies.ui.common.getFromLocationCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(application: Application): LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val geocoder = Geocoder(application)

    override suspend fun findLastLocation(): String? = findLastLocationP().toRegion()

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocationP(): Location =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }

    private suspend fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocationCompat(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }

}
