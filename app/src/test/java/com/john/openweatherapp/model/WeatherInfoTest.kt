package com.john.openweatherapp.model

import com.john.openweatherapp.DEFAULT_TEXT
import junit.framework.Assert.assertEquals
import org.junit.Test

class WeatherInfoTest {

    @Test
    fun createWeatherInfoAndVerify() {
        val model = WeatherInfo(
            weatherDescription = DEFAULT_TEXT,
            weatherIconUrl = DEFAULT_TEXT
        )
        with(model) {
            assertEquals(DEFAULT_TEXT, weatherDescription)
            assertEquals(DEFAULT_TEXT, weatherIconUrl)
        }
    }
}