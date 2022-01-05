package dev.jaym21.mausam.data.remote.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeelsLike(
    @Json(name = "day")
    val day: Double?,
    @Json(name = "night")
    val night: Double?,
    @Json(name = "eve")
    val eve: Double?,
    @Json(name = "morn")
    val morn: Int?
)