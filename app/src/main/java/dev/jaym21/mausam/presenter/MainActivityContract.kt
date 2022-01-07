package dev.jaym21.mausam.presenter

import dev.jaym21.mausam.data.local.WeatherEntity
import io.reactivex.Flowable

interface MainActivityContract {

    interface View {
        fun invokePresenterToCallApiForCurrentWeather()
        fun invokePresenterToCallApiForHourlyForecast()
    }

    interface Presenter {
        fun callApiToGetCurrentWeatherUsingLatLng(latitude: String, longitude: String)
        fun callApiToGetWeatherUsingCityName(cityName: String)
        fun callApiToGetHourlyForecast(latitude: String, longitude: String)
        fun getWeatherFromDatabase(): Flowable<List<WeatherEntity>>
        fun onActivityDestroy()
    }
}