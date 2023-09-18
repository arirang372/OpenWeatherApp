package com.john.openweatherapp.model

import com.john.openweatherapp.DEFAULT_DOUBLE
import com.john.openweatherapp.DEFAULT_LONG
import com.john.openweatherapp.DEFAULT_TEXT
import junit.framework.Assert.assertEquals
import org.junit.Test

class GeocodingDetailsTest {

    @Test
    fun createGeocodingDetailsWithDefaultConstructor() {
        val model = GeocodingDetails()
        with(model) {
            assertEquals(DEFAULT_LONG, id)
            assertEquals(0.0, lat)
            assertEquals(0.0, lon)
            assertEquals("", cityStateCountry)
        }
    }

    @Test
    fun createGeocodingDetailsWithCustomValues() {
        val model = GeocodingDetails(
            id = DEFAULT_LONG,
            lat = DEFAULT_DOUBLE,
            lon = DEFAULT_DOUBLE,
            cityStateCountry = DEFAULT_TEXT
        )
        with(model) {
            assertEquals(DEFAULT_LONG, id)
            assertEquals(DEFAULT_DOUBLE, lat)
            assertEquals(DEFAULT_DOUBLE, lon)
            assertEquals(DEFAULT_TEXT, cityStateCountry)
        }
    }
}