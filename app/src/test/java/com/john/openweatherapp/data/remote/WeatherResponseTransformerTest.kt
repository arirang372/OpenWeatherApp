package com.john.openweatherapp.data.remote

import com.john.openweatherapp.model.Coordinate
import junit.framework.Assert.assertEquals
import org.junit.Test

class WeatherResponseTransformerTest {

    private val transformer = WeatherResponseTransformer()
    private val response = OpenWeatherResponse(
        name = "London",
        cod = 200,
        id = 2643743,
        timezone = 3600,
        coord = Coordinate(
            lon = -0.13,
            lat = 51.51
        ),
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "clear sky",
                icon = "01n"
            )
        ),
        main = Main(
            temp = 61.66F,
            feels_like = 61.52F,
            temp_min = 58.8F,
            temp_max = 64.99F,
            pressure = 1017,
            humidity = 85
        ),
        visibility = 10000,
        wind = Wind(
            speed = 1.14F,
            deg = 0
        ),
        clouds = Clouds(
            all = 9
        ),
        sys = System(
            type = 2,
            id = 2000000,
            country = "GB",
            sunrise = 1693977633,
            sunset = 1694025475
        ),
        dt = 1693975666
    )

    @Test
    fun testWeatherResponseTransformer() {
        val weatherDetails = transformer.transform(response)
        with(weatherDetails) {
            assertEquals("London", city_name)
            assertEquals(-0.13, coordinate?.lon)
            assertEquals(51.51, coordinate?.lat)
            assertEquals(1, weather.size)
            assertEquals("clear sky", weather[0].weatherDescription)
            assertEquals("https://openweathermap.org/img/wn/01n@2x.png", weather[0].weatherIconUrl)
            assertEquals("61.66", current_temperature)
            assertEquals("61.52", feels_like_temperature)
            assertEquals("58.8", minimum_temperature)
            assertEquals("64.99", maximum_temperature)
            assertEquals("30.51", pressure)
            assertEquals("85", humidity)
            assertEquals("6.214", visibility)
            assertEquals("1.14", wind_speed)
        }
    }
}