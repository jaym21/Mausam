package dev.jaym21.mausam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.mausam.databinding.ActivityNext7DayForecastBinding
import dev.jaym21.mausam.presenter.Next7DayForecastContract
import dev.jaym21.mausam.presenter.Next7DayForecastPresenter

class Next7DayForecastActivity : AppCompatActivity(), Next7DayForecastContract.View {

    private var _binding: ActivityNext7DayForecastBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: Next7DayForecastContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNext7DayForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = Next7DayForecastPresenter()
    }

    override fun invokePresenterToGetNext7DayForecast() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onActivityDestroy()
        _binding = null
    }
}