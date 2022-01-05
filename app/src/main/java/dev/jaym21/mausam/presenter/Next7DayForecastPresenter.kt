package dev.jaym21.mausam.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jaym21.mausam.data.remote.models.responses.DailyForecastResponse
import dev.jaym21.mausam.data.remote.service.WeatherAPI
import dev.jaym21.mausam.utils.ApiResponse
import dev.jaym21.mausam.utils.DisposableManager
import io.reactivex.schedulers.Schedulers

class Next7DayForecastPresenter(private val api: WeatherAPI): Next7DayForecastContract.Presenter {

    private val _dailyForecast: MutableLiveData<ApiResponse<DailyForecastResponse>> = MutableLiveData()
    val dailyForecast: LiveData<ApiResponse<DailyForecastResponse>> = _dailyForecast

    override fun callApiToGet7DayForecast(latitude: String, longitude: String) {

        _dailyForecast.postValue(ApiResponse.Loading())

        DisposableManager.add(
            api.getDailyForecast(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { dailyForecast ->
                        _dailyForecast.postValue(ApiResponse.Success(dailyForecast))
                    },
                    { error ->
                        _dailyForecast.postValue(ApiResponse.Error("Could not get hourly forecast, $error"))
                    }
                )
        )
    }

    override fun onActivityDestroy() {
        DisposableManager.dispose()
    }
}