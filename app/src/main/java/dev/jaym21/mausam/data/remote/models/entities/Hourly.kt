package dev.jaym21.mausam.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    @Json(name = "dt")
    val dt: Int?,
    @Json(name = "temp")
    val temp: Double?,
    @Json(name = "feels_like")
    val feelsLike: Double?,
    @Json(name = "pressure")
    val pressure: Double?,
    @Json(name = "humidity")
    val humidity: Double?,
    @Json(name = "dew_point")
    val dewPoint: Double?,
    @Json(name = "uvi")
    val uvi: Double?,
    @Json(name = "clouds")
    val clouds: Double?,
    @Json(name = "visibility")
    val visibility: Double?,
    @Json(name = "wind_speed")
    val windSpeed: Double?,
    @Json(name = "wind_deg")
    val windDeg: Double?,
    @Json(name = "wind_gust")
    val windGust: Double?,
    @Json(name = "weather")
    val weather: List<Weather>?,
    @Json(name = "pop")
    val pop: Double?
)