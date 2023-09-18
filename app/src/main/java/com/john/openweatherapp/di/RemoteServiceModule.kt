package com.john.openweatherapp.di

import com.john.openweatherapp.data.OpenWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A dagger hilt module that will be injected for Retrofit Service for API calls
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class RemoteServiceModule {

    @Singleton
    @Provides
    fun providesOpenWeatherService(): OpenWeatherService {
        return OpenWeatherService.create()
    }
}