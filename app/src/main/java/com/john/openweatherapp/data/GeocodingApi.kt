package com.john.openweatherapp.data

import android.location.Address
import android.location.Geocoder
import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 *  The class that provides the geocoding based on the User's current location
 *
 */
class GeocodingApi @Inject constructor(
    private val geocoder: Geocoder
) {
    suspend fun getFromLocation(
        location: Location,
        maxResults: Int = 1
    ): List<Address> = withContext(Dispatchers.IO) {
        try {
            val addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                maxResults
            ) ?: emptyList()
            addresses
        } catch (e: IOException) {
            emptyList()
        }
    }
}
