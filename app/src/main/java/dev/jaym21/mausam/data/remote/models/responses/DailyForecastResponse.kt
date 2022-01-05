package dev.jaym21.mausam.data.remote.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.mausam.data.remote.models.entities.Daily

@JsonClass(generateAdapter = true)
data class DailyForecastResponse(
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "timezone")
    val timezone: String?,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int?,
    @Json(name = "daily")
    val daily: List<Daily>?
)