package dev.jaym21.mausam.presenter

interface Next7DayForecastContract {

    interface View {
        fun invokePresenterToGetNext7DayForecast()
    }

    interface Presenter {
        fun callApiToGet7DayForecast(latitude: String, longitude: String)
        fun onActivityDestroy()
    }
}