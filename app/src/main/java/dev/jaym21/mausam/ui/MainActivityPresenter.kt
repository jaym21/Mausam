package dev.jaym21.mausam.ui

import android.annotation.SuppressLint
import android.util.Log
import dev.jaym21.mausam.data.local.WeatherDatabase
import dev.jaym21.mausam.data.local.WeatherEntity
import dev.jaym21.mausam.data.remote.models.responses.HourlyForecastResponse
import dev.jaym21.mausam.data.remote.service.WeatherAPI
import dev.jaym21.mausam.utils.DisposableManager
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter (private val api: WeatherAPI, private val database: WeatherDatabase): MainActivityContract.Presenter {

    @SuppressLint("CheckResult")
    override fun callApiToGetCurrentWeather(cityName: String) {
        api.getWeatherForCity(cityName)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { cityResponse ->
                    val weather = WeatherEntity(0, cityResponse.coord?.lat, cityResponse.coord?.lat, cityResponse.name, cityResponse.main?.temp,
                        cityResponse.weather?.get(0)?.main, cityResponse.main?.humidity, cityResponse.wind?.speed
                    )
                    //caching weather data to use in case of no network
                    database.getWeatherDAO().deleteAllWeather()
                    database.getWeatherDAO().insertWeather(weather)
                },
                { error ->
                    Log.d("TAGYOYO", "callApiToGetWeather: $error")
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun callApiToGetHourlyForecast(latitude: String, longitude: String) {
        api.getHourlyForecast(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { hourlyForecastResponse ->

                },
                { error ->
                    Log.d("TAGYOYO", "callApiToGetHourlyForecast: $error")
                }
            )
    }

    override fun getWeatherFromDatabase(): Flowable<List<WeatherEntity>> {
        return database.getWeatherDAO().getAllWeather()
    }

    override fun onActivityDestroy() {
        DisposableManager.dispose()
    }
}