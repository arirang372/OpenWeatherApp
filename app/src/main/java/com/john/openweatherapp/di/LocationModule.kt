package com.john.openweatherapp.di

import android.app.Application
import android.location.Geocoder
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A dagger hilt module that will be injected for Geocoding and Location
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class LocationModule {

    @Provides
    @Singleton
    fun provideLocationProviderClient(application: Application) =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun provideGeocoder(application: Application) = Geocoder(application)
}