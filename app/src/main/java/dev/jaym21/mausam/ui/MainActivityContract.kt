package dev.jaym21.mausam.ui

import dev.jaym21.mausam.data.local.WeatherEntity
import io.reactivex.Flowable

interface MainActivityContract {

    interface View {
        fun invokePresenterToCallApi()
    }

    interface Presenter {
        fun callApiToGetWeather(cityName: String)
        fun getWeatherFromDatabase(): Flowable<List<WeatherEntity>>
        fun onActivityDestroy()
    }
}