package com.john.openweatherapp.data.remote

import com.john.openweatherapp.model.Coordinate
import com.john.openweatherapp.model.WeatherDetails

/**
 *  Classes used for Weather Related Apis
 *
 */
sealed class OpenWeatherResponseResult {

    data class Success(val details: WeatherDetails) : OpenWeatherResponseResult()

    data class Failure(val message: String) : OpenWeatherResponseResult()
}


data class OpenWeatherResponse(
    val coord: Coordinate,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: System,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    var icon: String = ""
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Float,
    val deg: Int
)

data class Clouds(
    val all: Int
)

data class System(
    val type: Int,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)