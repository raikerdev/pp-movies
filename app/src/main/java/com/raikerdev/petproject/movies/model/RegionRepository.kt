package com.raikerdev.petproject.movies.model

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import com.raikerdev.petproject.movies.ui.getFromLocationCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class RegionRepository(activity: AppCompatActivity) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val coarsePermissionChecker = PermissionChecker(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
    private val geocoder = Geocoder(activity)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? =
        if (coarsePermissionChecker.request()) findLastLocationSuspended()
        else null

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocationSuspended(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }

    private suspend fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocationCompat(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}