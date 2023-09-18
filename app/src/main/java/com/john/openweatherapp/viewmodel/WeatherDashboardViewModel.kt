package com.john.openweatherapp.viewmodel

import android.location.Address
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.john.openweatherapp.data.GeocodingApi
import com.john.openweatherapp.data.LocationApi
import com.john.openweatherapp.data.OpenWeatherRepository
import com.john.openweatherapp.data.local.SharedPreferencesManager
import com.john.openweatherapp.data.remote.OpenWeatherResponseResult
import com.john.openweatherapp.model.Coordinate
import com.john.openweatherapp.model.GeocodingDetails
import com.john.openweatherapp.model.WeatherDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A view model that handles the UI tasks for WeatherDashboardFragment
 *
 */

@HiltViewModel
class WeatherDashboardViewModel @Inject constructor(
    private val repository: OpenWeatherRepository,
    private val locationApi: LocationApi,
    private val geocodingApi: GeocodingApi
) : ViewModel() {

    private var _weatherResponseResult = MutableLiveData<OpenWeatherResponseResult>()
    val weatherResponseResult: LiveData<OpenWeatherResponseResult> = _weatherResponseResult
    var weatherDetails = ObservableField(WeatherDetails())
    var errorMessage = ObservableField("")
    val showLoadingBar = ObservableBoolean(false)
    val lastStoredLocations: LiveData<List<GeocodingDetails>>
        = repository.getLastStoredGeocodingDetails().asLiveData()

    var fetchedLastLocation = ObservableBoolean(false)

    private var _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>> = _addressList

    init {
        fetchedLastLocation.set(false)
    }

    fun fetchWeatherDetails(latitude: Double, longitude: Double) = viewModelScope.launch {
        showLoadingBar.set(true)
        val result = repository.getWeatherDetails(latitude, longitude)
        _weatherResponseResult.value = result
    }

    fun saveMyLocationCoordinate(sharedPreferencesManager: SharedPreferencesManager, coordinate: Coordinate) {
        sharedPreferencesManager.setMyLocationCoordinate(coordinate)
    }

    fun getCurrentAddress() {
        viewModelScope.launch {
            showLoadingBar.set(true)
            val location = locationApi.getCurrentLocation()
            val addresses = if (location != null) {
                geocodingApi.getFromLocation(location)
            } else {
                emptyList()
            }
            _addressList.value = addresses
            showLoadingBar.set(false)
        }
    }

    fun saveLastLocation(geocodingDetails: GeocodingDetails) {
        viewModelScope.launch {
            repository.saveGeocodingDetails(geocodingDetails)
        }
    }
}