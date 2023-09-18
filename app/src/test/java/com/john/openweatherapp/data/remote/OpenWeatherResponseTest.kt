package com.john.openweatherapp.data.remote

import com.john.openweatherapp.DEFAULT_DOUBLE
import com.john.openweatherapp.DEFAULT_FLOAT
import com.john.openweatherapp.DEFAULT_INT
import com.john.openweatherapp.DEFAULT_LONG
import com.john.openweatherapp.DEFAULT_TEXT
import com.john.openweatherapp.model.Coordinate
import org.junit.Assert
import org.junit.Test

class OpenWeatherResponseTest {

    @Test
    fun testCreateOpenWeatherResponseAndVerify() {
        val coord = Coordinate(
            lat = DEFAULT_DOUBLE,
            lon = DEFAULT_DOUBLE
        )
        val weathers = mutableListOf(
            Weather(
                id = DEFAULT_INT,
                main = DEFAULT_TEXT,
                description = DEFAULT_TEXT,
                icon = DEFAULT_TEXT
            )
        )
        val main = Main(
            temp = DEFAULT_FLOAT,
            feels_like = DEFAULT_FLOAT,
            temp_min = DEFAULT_FLOAT,
            temp_max = DEFAULT_FLOAT,
            pressure = DEFAULT_INT,
            humidity = DEFAULT_INT
        )
        val wind = Wind(
            speed = DEFAULT_FLOAT,
            deg = DEFAULT_INT
        )
        val clouds = Clouds(
            all = DEFAULT_INT
        )
        val system = System(
            type = DEFAULT_INT,
            id = DEFAULT_LONG,
            country = DEFAULT_TEXT,
            sunrise = DEFAULT_LONG,
            sunset = DEFAULT_LONG
        )


        val model = OpenWeatherResponse(
            coord = coord,
            weather = weathers,
            main = main,
            visibility = DEFAULT_INT,
            wind = wind,
            clouds = clouds,
            dt = DEFAULT_LONG,
            sys = system,
            timezone = DEFAULT_INT,
            id = DEFAULT_LONG,
            name = DEFAULT_TEXT,
            cod = DEFAULT_INT
        )
        with(model) {
            Assert.assertEquals(coord, this.coord)
            Assert.assertEquals(weathers, this.weather)
            Assert.assertEquals(main, this.main)
            Assert.assertEquals(DEFAULT_INT, visibility)
            Assert.assertEquals(wind, this.wind)
            Assert.assertEquals(clouds, this.clouds)
            Assert.assertEquals(DEFAULT_LONG, dt)
            Assert.assertEquals(system, this.sys)
            Assert.assertEquals(DEFAULT_INT, timezone)
            Assert.assertEquals(DEFAULT_LONG, id)
            Assert.assertEquals(DEFAULT_TEXT, name)
            Assert.assertEquals(DEFAULT_INT, cod)
        }
    }

    @Test
    fun testCreateSystemAndVerify() {
        val model = System(
            type = DEFAULT_INT,
            id = DEFAULT_LONG,
            country = DEFAULT_TEXT,
            sunrise = DEFAULT_LONG,
            sunset = DEFAULT_LONG
        )
        with(model) {
            Assert.assertEquals(DEFAULT_INT, type)
            Assert.assertEquals(DEFAULT_LONG, id)
            Assert.assertEquals(DEFAULT_TEXT, country)
            Assert.assertEquals(DEFAULT_LONG, sunrise)
            Assert.assertEquals(DEFAULT_LONG, sunset)
        }
    }

    @Test
    fun testCreateCloudsAndVerify() {
        val model = Clouds(
            all = DEFAULT_INT,
        )
        with(model) {
            Assert.assertEquals(DEFAULT_INT, all)
        }
    }

    @Test
    fun testCreateWindAndVerify() {
        val model = Wind(
            speed = DEFAULT_FLOAT,
            deg = DEFAULT_INT
        )
        with(model) {
            Assert.assertEquals(DEFAULT_FLOAT, speed)
            Assert.assertEquals(DEFAULT_INT, deg)
        }
    }

    @Test
    fun testCreateMainAndVerify() {
        val model = Main(
            temp = DEFAULT_FLOAT,
            feels_like = DEFAULT_FLOAT,
            temp_min = DEFAULT_FLOAT,
            temp_max = DEFAULT_FLOAT,
            pressure = DEFAULT_INT,
            humidity = DEFAULT_INT
        )
        with(model) {
            Assert.assertEquals(DEFAULT_FLOAT, temp)
            Assert.assertEquals(DEFAULT_FLOAT, feels_like)
            Assert.assertEquals(DEFAULT_FLOAT, temp_min)
            Assert.assertEquals(DEFAULT_FLOAT, temp_max)
            Assert.assertEquals(DEFAULT_INT, pressure)
            Assert.assertEquals(DEFAULT_INT, humidity)
        }
    }

    @Test
    fun testCreateWeatherAndVerify() {
        val model = Weather(
            id = DEFAULT_INT,
            main = DEFAULT_TEXT,
            description = DEFAULT_TEXT,
            icon = DEFAULT_TEXT
        )
        with(model) {
            Assert.assertEquals(DEFAULT_INT, id)
            Assert.assertEquals(DEFAULT_TEXT, main)
            Assert.assertEquals(DEFAULT_TEXT, description)
            Assert.assertEquals(DEFAULT_TEXT, icon)
        }
    }

}