package dev.jaym21.mausam.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "lat")
    val lat: Double?
)