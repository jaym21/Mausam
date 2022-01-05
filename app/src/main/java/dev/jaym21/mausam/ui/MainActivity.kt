package dev.jaym21.mausam.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jaym21.mausam.R
import dev.jaym21.mausam.adapters.HourlyForecastAdapter
import dev.jaym21.mausam.data.local.WeatherDatabase
import dev.jaym21.mausam.data.remote.service.WeatherAPI
import dev.jaym21.mausam.databinding.ActivityMainBinding
import dev.jaym21.mausam.utils.ApiResponse
import dev.jaym21.mausam.utils.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var presenter: MainActivityPresenter
    private var hourlyForecastAdapter = HourlyForecastAdapter()
    @Inject lateinit var api: WeatherAPI
    @Inject lateinit var database: WeatherDatabase

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing presenter
        presenter = MainActivityPresenter(api, database)

        invokePresenterToCallApiForCurrentWeather()

        invokePresenterToCallApiForHourlyForecast()

        setupRecyclerView()

        //adding observer on database weather data
        presenter.getWeatherFromDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weathers ->
                    Log.d("TAGYOYO", "onCreate: $weathers")
                    binding.tvLocation.text = weathers[0].name
                    binding.tvTemperature.text = weathers[0].temp.toString()
                    binding.tvDes.text = weathers[0].main
                    binding.tvHumidity.text = weathers[0].humidity.toString()
                    binding.tvWind.text = weathers[0].speed.toString()

                    when(weathers[0].main?.lowercase()) {
                        "clear" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_sun)
                            binding.clMain.setBackgroundResource(R.drawable.clear_bg)
                        }
                        "rain" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_rain)
                            binding.clMain.setBackgroundResource(R.drawable.rain_bg)
                        }
                        "drizzle" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_rain)
                            binding.clMain.setBackgroundResource(R.drawable.rain_bg)
                        }
                        "thunderstorm" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_storm)
                            binding.clMain.setBackgroundResource(R.drawable.thunderstorm_bg)
                        }
                        "snow" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_snowfall)
                            binding.clMain.setBackgroundResource(R.drawable.snow_bg)
                        }
                        "clouds" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_clouds)
                            binding.clMain.setBackgroundResource(R.drawable.clouds_bg)
                        }
                        "fog" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_foggy_day)
                            binding.clMain.setBackgroundResource(R.drawable.fog_bg)
                        }
                        "mist" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_mist)
                            binding.clMain.setBackgroundResource(R.drawable.fog_bg)
                        }
                        "smoke" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_mist)
                            binding.clMain.setBackgroundResource(R.drawable.smoke_bg)
                        }
                        "dust" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_mist)
                            binding.clMain.setBackgroundResource(R.drawable.smoke_bg)
                        }
                        "tornado" -> {
                            binding.ivWeatherIcon.setBackgroundResource(R.drawable.ic_tornado)
                            binding.clMain.setBackgroundResource(R.drawable.thunderstorm_bg)
                        }
                        "haze" -> {
                            binding.ivWeatherIcon.setImageResource(R.drawable.ic_mist)
                            binding.clMain.setBackgroundResource(R.drawable.fog_bg)
                        }
                    }
                },
                { error ->
                    Snackbar.make(binding.root, "Could not get weather data, $error", Snackbar.LENGTH_SHORT).show()
                }
            )

        //adding observer on hourly forecast livedata
        presenter.hourlyForecast.observe(this, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvTodayText.visibility = View.VISIBLE
                    binding.llNextForecast.visibility = View.VISIBLE
                    hourlyForecastAdapter.submitList(response.data?.hourly)
                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvTodayText.visibility = View.GONE
                    binding.llNextForecast.visibility = View.GONE
                    Log.d("TAGYOYO", "onCreate: ${response.responseMessage}")
                    Snackbar.make(binding.root, "${response.responseMessage}", Snackbar.LENGTH_SHORT).show()
                }
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun invokePresenterToCallApiForCurrentWeather() {
        presenter.callApiToGetCurrentWeather("mumbai")
    }

    override fun invokePresenterToCallApiForHourlyForecast() {
        val latitude = SharedPreferences.getCurrentLatitude(this)
        val longitude = SharedPreferences.getCurrentLongitude(this)

        if (!latitude.isNullOrBlank() && !longitude.isNullOrBlank()) {
            presenter.callApiToGetHourlyForecast(latitude, longitude)
        } else {
            Snackbar.make(binding.root,"Current location latitude longitude not found", Snackbar.LENGTH_SHORT).show()
            binding.tvTodayText.visibility = View.GONE
            binding.llNextForecast.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rvHourlyForecast.apply {
            adapter = hourlyForecastAdapter
            layoutManager = LinearLayoutManager(this@MainActivity , LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}