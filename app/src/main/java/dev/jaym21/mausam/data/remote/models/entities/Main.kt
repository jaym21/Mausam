package dev.jaym21.mausam.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double?,
    @Json(name = "feels_like")
    val feelsLike: Double?,
    @Json(name = "temp_min")
    val tempMin: Double?,
    @Json(name = "temp_max")
    val tempMax: Double?,
    @Json(name = "pressure")
    val pressure: Int?,
    @Json(name = "humidity")
    val humidity: Int?
)