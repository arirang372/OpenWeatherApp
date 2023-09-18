package com.john.openweatherapp.view

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.john.openweatherapp.PERMISSION_REQUEST_LOCATION
import com.john.openweatherapp.R
import com.john.openweatherapp.data.local.SharedPreferencesManager
import com.john.openweatherapp.data.remote.City
import com.john.openweatherapp.data.remote.GeocodingResponseResult
import com.john.openweatherapp.databinding.FragmentSearchWeatherBinding
import com.john.openweatherapp.model.Coordinate
import com.john.openweatherapp.model.GeocodingDetails
import com.john.openweatherapp.util.requestPermissionsCompat
import com.john.openweatherapp.view.adapters.CityDetailsRecyclerViewAdapter
import com.john.openweatherapp.viewmodel.SearchWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A UI class that renders UI for searching for the city names
 *
 */
@AndroidEntryPoint
class SearchWeatherFragment : Fragment(), SearchWeatherCallback {

    private val adapter = CityDetailsRecyclerViewAdapter(this)
    private lateinit var binding: FragmentSearchWeatherBinding
    private val viewModel: SearchWeatherViewModel by viewModels()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        subscribeLiveDataToUi()
        val myLocationCoordinate = sharedPreferencesManager.getMyLocationCoordinate()
        if (myLocationCoordinate != null) {
            viewModel.fetchReverseGeocodingDetails(
                myLocationCoordinate.lat,
                myLocationCoordinate.lon
            )
        } else {
            if (!sharedPreferencesManager.isLocationPermissionGranted()) {
                val cityDetails = mutableListOf<GeocodingDetails>().apply {
                    add(
                        GeocodingDetails(
                            cityStateCountry = getString(R.string.find_my_location)
                        )
                    )
                }
                adapter.setCityDetails(cityDetails)
            } else {
                subscribeLocationLiveDataToUi()
                viewModel.getCurrentAddress()
            }
        }
        binding.cityDetailsRecyclerView.adapter = adapter
    }

    private fun subscribeLiveDataToUi() {
        viewModel.geocodingResponseResult.observe(viewLifecycleOwner) {
            when (it) {
                is GeocodingResponseResult.Success -> {
                    adapter.setCityDetails(it.cities)
                    viewModel.showLoadingBar.set(false)
                    if (it.cities.isEmpty()) {
                        binding.searchErrorMessage.text = getString(R.string.no_search_result)
                        binding.searchErrorMessage.visibility = View.VISIBLE
                    }
                }

                is GeocodingResponseResult.Failure -> {
                    //for some reasons, databinding does not work for searchErrorMessage
                    //textfield so I manually set the error message here.
                    viewModel.searchErrorMessage.set(it.message)
                    viewModel.showLoadingBar.set(false)
                    binding.searchErrorMessage.text = it.message
                    binding.searchErrorMessage.visibility = View.VISIBLE
                }
            }
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
                viewModel.fetchReverseGeocodingDetails(
                    address.latitude,
                    address.longitude
                )
            }
        }
    }

    private fun setUpToolBar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        appCompatActivity.setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.search_bar)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.type_city_name)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.fetchGeocodingDetails(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCityRowClick(model: GeocodingDetails) {
        if (model.cityStateCountry.equals(getString(R.string.find_my_location), true)) {
            //request permissions
            (requireActivity() as AppCompatActivity).requestPermissionsCompat(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), PERMISSION_REQUEST_LOCATION
            )
        } else {
            //take user to WeatherDashboard page
            findNavController().navigate(
                SearchWeatherFragmentDirections.actionSearchWeatherFragmentToWeatherDashboardFragment()
                    .setGeoDetails(model)
            )
        }
    }
}