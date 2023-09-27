package com.john.openweatherapp.data

import com.john.openweatherapp.data.local.GeocodingDetailsDao
import com.john.openweatherapp.data.remote.GeocodingResponseResult
import com.john.openweatherapp.data.remote.OpenWeatherResponseResult
import com.john.openweatherapp.data.remote.WeatherResponseTransformer
import com.john.openweatherapp.model.GeocodingDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A repository that handles network operation or retrieve/store data from/into the database
 *
 */
@Singleton
class OpenWeatherRepository @Inject constructor(
    private val service: OpenWeatherService,
    private val geocodingDetailsDao: GeocodingDetailsDao
) {

    suspend fun getWeatherDetails(latitude: Double, longitude: Double): OpenWeatherResponseResult {
        return try {
            val serviceResponse = service.getWeatherData(latitude, longitude)
            OpenWeatherResponseResult.Success(
                WeatherResponseTransformer().transform(
                    serviceResponse
                )
            )

        } catch (exception: Exception) {
            OpenWeatherResponseResult.Failure(exception.message.toString())
        }
    }

    suspend fun getGeocodingDetails(cityName: String): GeocodingResponseResult {
        return try {
            val cityDetails = service.getGeocodingDetails(cityName)
            GeocodingResponseResult.Success(
                cityDetails.map {
                    GeocodingDetails(
                        lat = it.lat,
                        lon = it.lon,
                        cityStateCountry = "${it.name}, ${it.state}, ${it.country}"
                    )
                }
            )
        } catch (exception: Exception) {
            GeocodingResponseResult.Failure(exception.message.toString())
        }
    }

    suspend fun getReverseGeocodingDetails(
        latitude: Double,
        longitude: Double
    ): GeocodingResponseResult {
        return try {
            val cityDetails = service.getReverseGeocodingDetails(latitude, longitude)
            GeocodingResponseResult.Success(
                cityDetails.map {
                    GeocodingDetails(
                        lat = it.lat,
                        lon = it.lon,
                        cityStateCountry = "${it.name}, ${it.state}, ${it.country}"
                    )
                }
            )
        } catch (exception: Exception) {
            GeocodingResponseResult.Failure(exception.message.toString())
        }
    }

    suspend fun saveGeocodingDetails(geocodingDetails: GeocodingDetails) {
        val geoDetails = geocodingDetailsDao.getAllGeocodingDetails()
        if (!geoDetails.isNullOrEmpty()) {
            geocodingDetailsDao.deleteGeocodingDetails()
        }
        geocodingDetailsDao.insertGeocodingDetails(geocodingDetails)
    }

    fun getLastStoredGeocodingDetails(): Flow<List<GeocodingDetails>> {
        return geocodingDetailsDao.getAllGeocodingDetailsFlow()
    }
}

