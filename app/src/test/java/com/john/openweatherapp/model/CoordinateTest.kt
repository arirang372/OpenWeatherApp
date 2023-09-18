package com.john.openweatherapp.model

import com.john.openweatherapp.DEFAULT_DOUBLE
import junit.framework.Assert.assertEquals
import org.junit.Test

class CoordinateTest {

    @Test
    fun createCoordinateAndVerify() {
        val model = Coordinate(
            lon = DEFAULT_DOUBLE,
            lat = DEFAULT_DOUBLE
        )
        with(model) {
            assertEquals(DEFAULT_DOUBLE, lon)
            assertEquals(DEFAULT_DOUBLE, lat)
        }
    }
}