package com.john.openweatherapp.data.remote

import com.john.openweatherapp.model.GeocodingDetails

/**
 *  Classes used for Geocoding related apis
 *
 */
sealed class GeocodingResponseResult {
    data class Success(val cities: List<GeocodingDetails>) : GeocodingResponseResult()
    data class Failure(val message: String) : GeocodingResponseResult()
}

data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)