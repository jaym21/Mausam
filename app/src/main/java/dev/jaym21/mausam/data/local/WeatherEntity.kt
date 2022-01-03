package dev.jaym21.mausam.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lon: Double?,
    val lat: Double?,
    val name: String?,
    val temp: Double?,
    val main: String?,
    val humidity: Int?,
    val speed: Double?,
)