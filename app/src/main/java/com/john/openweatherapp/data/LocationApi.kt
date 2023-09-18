
package com.john.openweatherapp.data

import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 *  The class that provides the User's location, which will be passed into [GeocodingApi]
 *  if the location permission is provided
 *
 */
class LocationApi @Inject constructor(
    private val locationProvider: FusedLocationProviderClient
) {

    suspend fun getCurrentLocation(): Location? {
        val cancellationTokenSource = CancellationTokenSource()
        return try {
            locationProvider.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await(cancellationTokenSource)
        } catch (e: SecurityException) {
            Log.w("LocationApi", "Couldn't get location, did you request location permissions?", e)
            null
        }
    }
}
