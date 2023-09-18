package com.john.openweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.john.openweatherapp.DEFAULT_LATITUDE
import com.john.openweatherapp.DEFAULT_LONGITUDE
import com.john.openweatherapp.data.local.SharedPreferencesManager
import com.john.openweatherapp.data.remote.OpenWeatherResponseResult
import com.john.openweatherapp.databinding.FragmentWeatherDashboardBinding
import com.john.openweatherapp.model.GeocodingDetails
import com.john.openweatherapp.view.adapters.WeatherDataRecyclerViewAdapter
import com.john.openweatherapp.viewmodel.WeatherDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A UI class that renders UI for displaying the weather data
 *
 */
@AndroidEntryPoint
class WeatherDashboardFragment : Fragment() {

    private val adapter = WeatherDataRecyclerViewAdapter()
    private lateinit var binding: FragmentWeatherDashboardBinding
    private val viewModel: WeatherDashboardViewModel by viewModels()
    private var geoDetails: GeocodingDetails? = null
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.weatherDetailsRecyclerView.adapter = adapter

        val args: WeatherDashboardFragmentArgs by navArgs()
        geoDetails = args.geoDetails

        if (sharedPreferencesManager.isLocationPermissionGranted()
            && sharedPreferencesManager.isAppOpenFirstTime()
        ) {
            subscribeLocationLiveDataToUi()
            viewModel.getCurrentAddress()
        } else {
            subscribeLastStoredLocationLiveDataToUi()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveDataToUi()
        binding.toolbar.setNavigationOnClickListener {
            //navigate to the Search Weather Fragment...
            findNavController().navigate(
                WeatherDashboardFragmentDirections.actionWeatherDashboardFragmentToSearchWeatherFragment()
            )
        }
    }

    private fun subscribeLocationLiveDataToUi() {
        viewModel.addressList.observe(viewLifecycleOwner) { addresses ->
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val geocodingDetails = GeocodingDetails()
                geocodingDetails.lat = address.latitude
                geocodingDetails.lon = address.longitude
                viewModel.saveLastLocation(geocodingDetails)
                viewModel.fetchWeatherDetails(
                    address.latitude,
                    address.longitude
                )
            }
        }
    }

    private fun subscribeLiveDataToUi() {
        viewModel.weatherResponseResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is OpenWeatherResponseResult.Success -> {
                    if (sharedPreferencesManager.isAppOpenFirstTime()) {
                        if (sharedPreferencesManager.isLocationPermissionGranted()) {
                            result.details.coordinate?.let { coord ->
                                viewModel.saveMyLocationCoordinate(
                                    sharedPreferencesManager,
                                    coord
                                )
                            }
                        }
                        sharedPreferencesManager.setAppOpenFirstTime(false)
                    }

                    adapter.setWeatherInfo(result.details.weather)
                    viewModel.weatherDetails.set(result.details)
                    viewModel.showLoadingBar.set(false)
                }

                is OpenWeatherResponseResult.Failure -> {
                    viewModel.errorMessage.set(result.message)
                    viewModel.showLoadingBar.set(false)
                }
            }
        }

    }

    private fun subscribeLastStoredLocationLiveDataToUi() {
        viewModel.lastStoredLocations.observe(viewLifecycleOwner) { lastStoredLocation ->
            if (!viewModel.fetchedLastLocation.get()) {
                if (geoDetails == null) {
                    //if the stored geoDetails is available -> Use that info to display the page.
                    if (lastStoredLocation.isNullOrEmpty())
                        viewModel.fetchWeatherDetails(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                    else {
                        viewModel.fetchWeatherDetails(
                            lastStoredLocation[0].lat,
                            lastStoredLocation[0].lon
                        )
                    }
                } else {
                    //store the object into the database
                    viewModel.saveLastLocation(geoDetails!!)
                    viewModel.fetchWeatherDetails(geoDetails!!.lat, geoDetails!!.lon)
                    viewModel.fetchedLastLocation.set(true)
                }
            }
        }
    }
}