package com.meowsoft.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.meowsoft.weatherapp.data.common.toForecastLocation
import com.meowsoft.weatherapp.domain.common.Result
import com.meowsoft.weatherapp.domain.location.LocationTracker
import com.meowsoft.weatherapp.domain.location.model.ForecastLocation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val locationProviderClient: FusedLocationProviderClient,
    private val geocoder: Geocoder,
    private val application: Application
) : LocationTracker {

    override suspend fun getLocationByCityName(locationName: String): Result<ForecastLocation> {
        return suspendCancellableCoroutine { continuation ->
            try {
                geocoder.getFromLocationName(locationName, 1) { address ->
                    if (address.isEmpty()) {
                        val result = Result.Error<ForecastLocation>("LocationNotFound")
                        continuation.resume(result)
                    } else {
                        val location = address.first().toForecastLocation()
                        val result = Result.Success(location)
                        continuation.resume(result)
                    }
                }
            } catch (e: Exception) {
                continuation.cancel(e)
            }
        }
    }

    override suspend fun getLocation(): Location? {
        val hasPermissionFineLocation = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasPermissionCoarseLocation = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationService = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationService.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationService.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (isGpsEnabled.not() || hasPermissionCoarseLocation.not() || hasPermissionFineLocation.not()) {
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            locationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result)
                    } else {
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnFailureListener {
                    continuation.resume(null)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}
