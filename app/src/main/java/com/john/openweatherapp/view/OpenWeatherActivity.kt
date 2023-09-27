package com.john.openweatherapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.john.openweatherapp.PERMISSION_REQUEST_LOCATION
import com.john.openweatherapp.R
import com.john.openweatherapp.data.local.SharedPreferencesManager
import com.john.openweatherapp.databinding.ActivityOpenWeatherBinding
import com.john.openweatherapp.util.requestPermissionsCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenWeatherActivity : AppCompatActivity() {

    var locationDeniedActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (RESULT_OK == result.resultCode) {
            requestPermissionsCompat(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), PERMISSION_REQUEST_LOCATION
            )
            return@registerForActivityResult
        }
        return@registerForActivityResult
    }

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityOpenWeatherBinding>(
            this,
            R.layout.activity_open_weather
        )
        sharedPreferencesManager = SharedPreferencesManager(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.size == 2 && grantResults[0] != PackageManager.PERMISSION_GRANTED &&
                grantResults[1] != PackageManager.PERMISSION_GRANTED
            ) {
                //permission is not granted
                sharedPreferencesManager.setLocationPermissionGranted(false)

            } else {
                //permission is granted either Coarse or Fine
                sharedPreferencesManager.setLocationPermissionGranted(true)
            }
            if (!sharedPreferencesManager.isAppOpenFirstTime())
                sharedPreferencesManager.setAppOpenFirstTime(true)

            if (sharedPreferencesManager.isLocationPermissionGranted()) {
                findNavController(R.id.nav_open_weather)
                    .navigate(
                        SearchWeatherFragmentDirections
                            .actionSearchWeatherFragmentToWeatherDashboardFragment()
                    )
            } else {
                //take user to Location access denied page
                val intent = Intent(this, LocationDeniedActivity::class.java)
                locationDeniedActivity.launch(intent)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}