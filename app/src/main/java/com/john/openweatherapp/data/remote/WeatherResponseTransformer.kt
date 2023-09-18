package com.john.openweatherapp.data.remote

import com.john.openweatherapp.model.WeatherDetails
import com.john.openweatherapp.model.WeatherInfo
import kotlin.math.roundToInt


/**
 *  A mapper that transforms the response data fields into the ui object
 *
 */

class WeatherResponseTransformer {

    fun transform(response: OpenWeatherResponse): WeatherDetails {
        return WeatherDetails(
            city_name = response.name,
            coordinate = response.coord,
            weather = transformWeather(response.weather),
            current_temperature = response.main.temp.toString(),
            feels_like_temperature = response.main.feels_like.toString(),
            minimum_temperature = response.main.temp_min.toString(),
            maximum_temperature = response.main.temp_max.toString(),
            pressure = ((response.main.pressure * 0.03 * 1000).roundToInt() / 1000.0).toString(),
            humidity = response.main.humidity.toString(),
            visibility = (( (response.visibility) * 0.621371).roundToInt() / 1000.0).toString(),
            wind_speed = response.wind.speed.toString()
        )
    }

    private fun transformWeather(weathers: List<Weather>) =
        mutableListOf<WeatherInfo>().apply {
            weathers.forEach {
                add(
                    WeatherInfo(
                        weatherDescription = it.description,
                        weatherIconUrl = "https://openweathermap.org/img/wn/${it.icon}@2x.png"
                    )
                )
            }
        }
}