package com.john.openweatherapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.john.openweatherapp.data.local.SharedPreferencesManager
import com.john.openweatherapp.util.checkSelfPermissionCompat
import com.john.openweatherapp.util.requestPermissionsCompat
import com.john.openweatherapp.view.OpenWeatherActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * An empty UI class that shows the Location permission dialog at the start of the app.
 */
@AndroidEntryPoint
class LocationPermissionActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_permission)
        sharedPreferencesManager = SharedPreferencesManager(this)
        showLocationPermissionDialog()
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
            navigateToNextPage()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showLocationPermissionDialog() {
        if (checkSelfPermissionCompat(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            checkSelfPermissionCompat(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            //permission is already granted.
            navigateToNextPage()

        } else {
            requestLocationPermission()
        }
    }

    private fun navigateToNextPage() {
        startActivity(Intent(this, OpenWeatherActivity::class.java))
        this.finish()
    }

    private fun requestLocationPermission() {
        requestPermissionsCompat(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_REQUEST_LOCATION
        )
    }
}