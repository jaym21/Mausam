package dev.jaym21.mausam.ui

import dev.jaym21.mausam.utils.DisposableManager

class MainActivityPresenter(view: MainActivityContract.View): MainActivityContract.Presenter {

    override fun callApiToGetWeather(cityName: String) {

    }

    override fun onActivityDestroy() {
        DisposableManager.dispose()
    }
}