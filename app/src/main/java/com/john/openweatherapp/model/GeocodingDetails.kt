package com.john.openweatherapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  An model object that is used for the UI. It is passed around between Fragments and stored in the room database
 *
 */

@Parcelize
@Entity(tableName = "geocodingDetails")
data class GeocodingDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "lat")
    var lat: Double = 0.0,
    @ColumnInfo(name = "lon")
    var lon: Double = 0.0,
    @ColumnInfo(name = "city_state_country")
    var cityStateCountry: String = "",
) : Parcelable