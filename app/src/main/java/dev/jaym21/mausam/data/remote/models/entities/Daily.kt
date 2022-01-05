package dev.jaym21.mausam.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Daily(
    @Json(name = "dt")
    val dt: Int?,
    @Json(name = "sunrise")
    val sunrise: Int?,
    @Json(name = "sunset")
    val sunset: Int?,
    @Json(name = "moonrise")
    val moonrise: Int?,
    @Json(name = "moonset")
    val moonset: Int?,
    @Json(name = "moon_phase")
    val moonPhase: Double?,
    @Json(name = "temp")
    val temp: Temp?,
    @Json(name = "feels_like")
    val feelsLike: FeelsLike?,
    @Json(name = "pressure")
    val pressure: Int?,
    @Json(name = "humidity")
    val humidity: Int?,
    @Json(name = "dew_point")
    val dewPoint: Double?,
    @Json(name = "wind_speed")
    val windSpeed: Double?,
    @Json(name = "wind_deg")
    val windDeg: Int?,
    @Json(name = "wind_gust")
    val windGust: Double?,
    @Json(name = "weather")
    val weather: List<Weather>?,
    @Json(name = "clouds")
    val clouds: Int?,
    @Json(name = "pop")
    val pop: Double?,
    @Json(name = "uvi")
    val uvi: Double?
)