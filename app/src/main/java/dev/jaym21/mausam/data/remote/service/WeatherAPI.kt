package dev.jaym21.mausam.data.remote.service

import dev.jaym21.mausam.data.remote.models.responses.CityResponse
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
        apiKey: String = "e8831806d35a5163760fe42bc92a80db"
    ): Single<CityResponse>
}