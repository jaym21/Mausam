package dev.jaym21.mausam.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "main")
    val main: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "icon")
    val icon: String?
)