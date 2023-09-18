package com.john.openweatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.john.openweatherapp.model.GeocodingDetails
import kotlinx.coroutines.flow.Flow

/**
 * A data access object used to execute the queries for room database
 *
 */

@Dao
interface GeocodingDetailsDao {

    @Query("SELECT * FROM geocodingDetails")
    suspend fun getAllGeocodingDetails(): MutableList<GeocodingDetails>

    @Query("SELECT * FROM geocodingDetails")
    fun getAllGeocodingDetailsFlow(): Flow<List<GeocodingDetails>>

    @Query("SELECT * FROM geocodingDetails WHERE id =:id")
    fun getGeocodingDetails(id: Long): Flow<GeocodingDetails?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeocodingDetails(geocodingDetails: GeocodingDetails)

    @Query("DELETE FROM geocodingDetails")
    suspend fun deleteGeocodingDetails()
}