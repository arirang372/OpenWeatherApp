package com.john.openweatherapp.viewmodel

import android.location.Address
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.openweatherapp.data.GeocodingApi
import com.john.openweatherapp.data.LocationApi
import com.john.openweatherapp.data.OpenWeatherRepository
import com.john.openweatherapp.data.remote.GeocodingResponseResult
import com.john.openweatherapp.model.GeocodingDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A view model that handles the UI tasks for SearchWeatherFragment
 *
 */
@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val repository: OpenWeatherRepository,
    private val locationApi: LocationApi,
    private val geocodingApi: GeocodingApi
) : ViewModel() {

    private var _geocodingResponseResult = MutableLiveData<GeocodingResponseResult>()
    val geocodingResponseResult : LiveData<GeocodingResponseResult> = _geocodingResponseResult
    var searchErrorMessage = ObservableField("")
    val showLoadingBar = ObservableBoolean(false)
    private var _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>> = _addressList

    fun fetchGeocodingDetails(cityName: String) = viewModelScope.launch {
        showLoadingBar.set(true)
        val result = repository.getGeocodingDetails(cityName)
        _geocodingResponseResult.value = result
    }

    fun fetchReverseGeocodingDetails(latitude: Double, longitude: Double) = viewModelScope.launch {
        showLoadingBar.set(true)
        val result = repository.getReverseGeocodingDetails(latitude, longitude)
        _geocodingResponseResult.value = result
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