package com.kasra.weather.data.location


import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
/**
 * Class responsible for tracking the current location using FusedLocationProviderClient.
 * Requires location permissions (ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION) to function properly.
 */
class LocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) {

    /**
     * Retrieves the current location asynchronously.*
     * Checks for location permissions and GPS availability before attempting to fetch the location.
     * Returns null if permissions are not granted or GPS is disabled.
     *
     * @return The current [Location] object if successful, null otherwise.
     */
    suspend fun getCurrentLocation(): Location? {
        // Check for location permissions
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        // Check if GPS is enabled
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply { // Check if the last location is already available
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)// Resume with the location result
                    } else {
                        cont.resume(null)// Resume with null if unsuccessful
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it)// Resume with the location on success
                }
                addOnFailureListener {
                    cont.resume(null)// Resume with null on failure
                }
                addOnCanceledListener {
                    cont.cancel()// Cancel the coroutine if the location request is canceled
                }
            }
        }
    }

}