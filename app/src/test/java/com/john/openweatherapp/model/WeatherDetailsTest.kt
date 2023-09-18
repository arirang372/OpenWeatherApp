package com.john.openweatherapp.model

import com.john.openweatherapp.DEFAULT_DOUBLE
import com.john.openweatherapp.DEFAULT_LONG
import com.john.openweatherapp.DEFAULT_TEXT
import junit.framework.Assert.assertEquals
import org.junit.Test

class WeatherDetailsTest {

    @Test
    fun createWeatherDetailsWithDefaultConstructor() {
        val model = WeatherDetails()
        with(model) {
            assertEquals(DEFAULT_LONG, id)
            assertEquals(null, coordinate)
            assertEquals("", city_name)
            assertEquals("", weather_description)
            assertEquals(0, weather.size)
            assertEquals("", current_temperature)
            assertEquals("", feels_like_temperature)
            assertEquals("", minimum_temperature)
            assertEquals("", maximum_temperature)
            assertEquals("", pressure)
            assertEquals("", humidity)
            assertEquals("", visibility)
            assertEquals("", wind_speed)
        }
    }

    @Test
    fun createWeatherDetailsWithCustomValues() {
        val coordinate = Coordinate(
            lat = 50.0,
            lon = -13.5
        )
        val weather = mutableListOf<WeatherInfo>()
        val model = WeatherDetails(
            id = DEFAULT_LONG,
            coordinate = coordinate,
            city_name = DEFAULT_TEXT,
            weather_description = DEFAULT_TEXT,
            weather = weather,
            current_temperature = DEFAULT_TEXT,
            feels_like_temperature = DEFAULT_TEXT,
            minimum_temperature = DEFAULT_TEXT,
            maximum_temperature = DEFAULT_TEXT,
            pressure = DEFAULT_TEXT,
            humidity = DEFAULT_TEXT,
            visibility = DEFAULT_TEXT,
            wind_speed = DEFAULT_TEXT,
        )
        with(model) {
            assertEquals(DEFAULT_LONG, id)
            assertEquals(coordinate, this.coordinate)
            assertEquals(DEFAULT_TEXT, city_name)
            assertEquals(DEFAULT_TEXT, weather_description)
            assertEquals(0, weather.size)
            assertEquals(DEFAULT_TEXT, current_temperature)
            assertEquals(DEFAULT_TEXT, feels_like_temperature)
            assertEquals(DEFAULT_TEXT, minimum_temperature)
            assertEquals(DEFAULT_TEXT, maximum_temperature)
            assertEquals(DEFAULT_TEXT, pressure)
            assertEquals(DEFAULT_TEXT, humidity)
            assertEquals(DEFAULT_TEXT, visibility)
            assertEquals(DEFAULT_TEXT, wind_speed)
        }
    }
}