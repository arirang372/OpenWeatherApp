package com.john.openweatherapp.data

import android.util.Log
import com.john.openweatherapp.BuildConfig
import com.john.openweatherapp.data.remote.City
import com.john.openweatherapp.data.remote.OpenWeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API that invokes API calls
 *
 */

interface OpenWeatherService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): OpenWeatherResponse


    @GET("/geo/1.0/direct")
    suspend fun getGeocodingDetails(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): List<City>

    @GET("/geo/1.0/reverse")
    suspend fun getReverseGeocodingDetails(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): List<City>


    companion object {
        private const val BASE_URL = "https://api.openweathermap.org"
        private const val DEFAULT_LIMIT = 5
        fun create(): OpenWeatherService {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(logger)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService::class.java)

        }
    }
}