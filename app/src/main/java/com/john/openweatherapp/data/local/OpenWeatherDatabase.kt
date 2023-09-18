package com.john.openweatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.john.openweatherapp.model.GeocodingDetails

/**
 * A room database for storing the structured data
 */

@Database(entities = [GeocodingDetails::class], version = 1, exportSchema = false)
abstract class OpenWeatherDatabase : RoomDatabase() {

    abstract fun geocodingDetailsDao(): GeocodingDetailsDao

    companion object {

        @Volatile
        private var instance: OpenWeatherDatabase? = null

        fun getInstance(context: Context): OpenWeatherDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): OpenWeatherDatabase {
            return Room.databaseBuilder(
                context, OpenWeatherDatabase::class.java,
                "OpenWeather.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}