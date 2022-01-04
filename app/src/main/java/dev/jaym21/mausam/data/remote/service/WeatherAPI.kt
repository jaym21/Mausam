package dev.jaym21.mausam.data.remote.service

import dev.jaym21.mausam.data.remote.models.responses.CityResponse
import dev.jaym21.mausam.data.remote.models.responses.HourlyForecastResponse
import dev.jaym21.mausam.utils.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather?")
    fun getWeatherForCity (
        @Query("q")
        cityName: String,
        @Query("units")
        unit: String = "metric",
        @Query("appid")
        apiKey: String = Constants.API_KEY
    ): Single<CityResponse>

    @GET("weather?")
    fun getWeatherByLatLng (
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String,
        @Query("units")
        unit: String = "metric",
        @Query("appid")
        apiKey: String = Constants.API_KEY
    ): Single<CityResponse>

    @GET("onecall?")
    fun getHourlyForecast (
        @Query("lat")
        latitude: String,
        @Query("lon")
        longitude: String,
        @Query("exclude")
        exclude: String = "current,minutely,daily,alerts",
        @Query("units")
        unit: String = "metric",
        @Query("appid")
        apiKey: String = "e8831806d35a5163760fe42bc92a80db"
    ): Single<HourlyForecastResponse>
}