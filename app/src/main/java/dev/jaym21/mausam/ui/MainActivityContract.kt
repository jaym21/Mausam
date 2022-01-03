package dev.jaym21.mausam.ui

interface MainActivityContract {

    interface View {
        fun invokePresenterToCallApi()
    }

    interface Presenter {
        fun callApiToGetWeather(cityName: String)
        fun onActivityDestroy()
    }
}