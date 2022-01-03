package dev.jaym21.mausam.data.remote.service

import dev.jaym21.mausam.data.remote.models.responses.CityResponse
import dev.jaym21.mausam.utils.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather?")
    suspend fun getWeatherForCity (
        @Query("q")
        cityName: String,
        @Query("units")
        unit: String = "metric",
        @Query("appid")
        apiKey: String = Constants.API_KEY
    ): Single<CityResponse>

    @GET("weather?")
    suspend fun getWeatherByLatLng (
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String,
        @Query("units")
        unit: String = "metric",
        @Query("appid")
        apiKey: String = Constants.API_KEY
    ): Single<CityResponse>
}