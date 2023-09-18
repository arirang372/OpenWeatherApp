package com.john.openweatherapp.data.local

import android.content.Context
import com.google.gson.Gson
import com.john.openweatherapp.R
import com.john.openweatherapp.model.Coordinate

/**
 * A class that stores a piece of data that are not structured for the app flow.
 *
 */

open class SharedPreferencesManager(val context: Context) {
    private val sharedPref = context.getSharedPreferences(
        context.getString(R.string.shared_preferences_key),
        Context.MODE_PRIVATE
    )

    open fun getSharedPref() = sharedPref

    fun setMyLocationCoordinate(myLocation: Coordinate) {
        val json = Gson().toJson(myLocation)
        writeString(context.getString(R.string.my_location), json)
    }

    fun getMyLocationCoordinate(): Coordinate? {
        val storedJson = getSharedPref().getString(context.getString(R.string.my_location), "")
        return if (storedJson.isNullOrEmpty())
            null
        else
            Gson().fromJson(storedJson, Coordinate::class.java)
    }

    fun setLocationPermissionGranted(isGranted: Boolean) {
        writeBoolean(context.getString(R.string.is_location_permission_granted), isGranted)
    }

    fun isLocationPermissionGranted(): Boolean {
        return getSharedPref().getBoolean(
            context.getString(R.string.is_location_permission_granted),
            false
        )
    }

    fun setAppOpenFirstTime(isFirstTime: Boolean) {
        writeBoolean(context.getString(R.string.is_app_open_first_time), isFirstTime)
    }

    fun isAppOpenFirstTime(): Boolean {
        return getSharedPref().getBoolean(context.getString(R.string.is_app_open_first_time), false)
    }

    private fun writeBoolean(key: String, value: Boolean) {
        with(getSharedPref().edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    private fun writeString(key: String, value: String) {
        with(getSharedPref().edit()) {
            putString(key, value)
            apply()
        }
    }
}