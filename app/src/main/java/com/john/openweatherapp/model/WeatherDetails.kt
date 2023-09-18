package com.john.openweatherapp.model

/**
 *  An model object that is used for the UI
 *
 */
data class WeatherDetails(
    var id: Long = 0,
    var coordinate: Coordinate? = null,
    var city_name: String = "",
    var weather_description: String = "", //json string of WeatherInfo
    var weather: List<WeatherInfo> = emptyList(), //weather info transformed from weather_description
    var current_temperature: String = "",
    var feels_like_temperature: String = "",
    var minimum_temperature: String = "",
    var maximum_temperature: String = "",
    var pressure: String = "",
    var humidity: String = "",
    var visibility: String = "",
    var wind_speed: String = ""
)