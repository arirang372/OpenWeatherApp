package com.john.openweatherapp.view

import com.john.openweatherapp.model.GeocodingDetails

/**
 * Callback that is used by user's action. e.g. City row click on [SearchWeatherFragment]
 *
 */
interface SearchWeatherCallback {
    fun onCityRowClick(model: GeocodingDetails)
}