package com.john.openweatherapp.data.remote

import com.john.openweatherapp.DEFAULT_DELTA
import com.john.openweatherapp.DEFAULT_DOUBLE
import com.john.openweatherapp.DEFAULT_TEXT
import org.junit.Assert.assertEquals
import org.junit.Test

class CityTest {

    @Test
    fun testCreateCityAndVerify() {
        val city = City(
            name = DEFAULT_TEXT,
            lat = DEFAULT_DOUBLE,
            lon = DEFAULT_DOUBLE,
            country = DEFAULT_TEXT,
            state = DEFAULT_TEXT
        )
        with(city) {
            assertEquals(DEFAULT_TEXT, name)
            assertEquals(DEFAULT_DOUBLE, lat, DEFAULT_DELTA)
            assertEquals(DEFAULT_DOUBLE, lon, DEFAULT_DELTA)
            assertEquals(DEFAULT_TEXT, country)
            assertEquals(DEFAULT_TEXT, state)
        }
    }
}