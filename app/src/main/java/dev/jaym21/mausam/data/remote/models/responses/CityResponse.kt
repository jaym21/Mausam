package dev.jaym21.mausam.data.remote.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.mausam.data.remote.models.entities.*

@JsonClass(generateAdapter = true)
data class CityResponse(
    @Json(name = "coord")
    val coord: Coord?,
    @Json(name = "weather")
    val weather: List<Weather>?,
    @Json(name = "base")
    val base: String?,
    @Json(name = "main")
    val main: Main?,
    @Json(name = "visibility")
    val visibility: Int?,
    @Json(name = "wind")
    val wind: Wind?,
    @Json(name = "clouds")
    val clouds: Clouds?,
    @Json(name = "dt")
    val dt: Int?,
    @Json(name = "sys")
    val sys: Sys?,
    @Json(name = "timezone")
    val timezone: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "cod")
    val cod: Int?
)