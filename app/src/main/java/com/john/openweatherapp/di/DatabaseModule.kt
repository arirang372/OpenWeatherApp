package com.john.openweatherapp.di

import android.content.Context
import com.john.openweatherapp.data.local.GeocodingDetailsDao
import com.john.openweatherapp.data.local.OpenWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A dagger hilt module that will be injected for database
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): OpenWeatherDatabase {
        return OpenWeatherDatabase.getInstance(context)
    }

    @Provides
    fun provideGeocodingDetailsDao(database: OpenWeatherDatabase): GeocodingDetailsDao {
        return database.geocodingDetailsDao()
    }
}