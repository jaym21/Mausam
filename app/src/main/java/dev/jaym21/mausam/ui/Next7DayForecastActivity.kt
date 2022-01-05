package dev.jaym21.mausam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.jaym21.mausam.adapters.DailyForecastAdapter
import dev.jaym21.mausam.data.remote.service.WeatherAPI
import dev.jaym21.mausam.databinding.ActivityNext7DayForecastBinding
import dev.jaym21.mausam.presenter.Next7DayForecastContract
import dev.jaym21.mausam.presenter.Next7DayForecastPresenter
import dev.jaym21.mausam.utils.ApiResponse
import dev.jaym21.mausam.utils.SharedPreferences
import javax.inject.Inject

@AndroidEntryPoint
class Next7DayForecastActivity : AppCompatActivity(), Next7DayForecastContract.View {

    private var _binding: ActivityNext7DayForecastBinding? = null
    private val binding get() = _binding!!
    private var dailyForecastAdapter = DailyForecastAdapter()
    private lateinit var presenter: Next7DayForecastPresenter
    @Inject lateinit var api: WeatherAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNext7DayForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = Next7DayForecastPresenter(api)

        setUpRecyclerView()

        invokePresenterToGetNext7DayForecast()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        //adding observer on daily forecast livedata
        presenter.dailyForecast.observe(this, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    dailyForecastAdapter.submitList(response.data?.daily)
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("TAGYOYO", "onCreate: ${response.responseMessage}")
                    Snackbar.make(binding.root, "${response.responseMessage}", Snackbar.LENGTH_SHORT).show()
                }
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun invokePresenterToGetNext7DayForecast() {
        val latitude = SharedPreferences.getCurrentLatitude(this)
        val longitude = SharedPreferences.getCurrentLongitude(this)

        if (!latitude.isNullOrBlank() && !longitude.isNullOrBlank()) {
            presenter.callApiToGet7DayForecast(latitude, longitude)
        } else {
            Snackbar.make(binding.root,"Current location latitude longitude not found", Snackbar.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    private fun setUpRecyclerView() {
        binding.rvDailyForecast.apply {
            adapter = dailyForecastAdapter
            layoutManager = LinearLayoutManager(this@Next7DayForecastActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onActivityDestroy()
        _binding = null
    }
}