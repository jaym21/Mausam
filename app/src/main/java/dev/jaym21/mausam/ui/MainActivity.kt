package dev.jaym21.mausam.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jaym21.mausam.R
import dev.jaym21.mausam.adapters.HourlyForecastAdapter
import dev.jaym21.mausam.data.local.WeatherDatabase
import dev.jaym21.mausam.data.remote.service.WeatherAPI
import dev.jaym21.mausam.databinding.ActivityMainBinding
import dev.jaym21.mausam.presenter.MainActivityContract
import dev.jaym21.mausam.presenter.MainActivityPresenter
import dev.jaym21.mausam.utils.ApiResponse
import dev.jaym21.mausam.utils.SharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var presenter: MainActivityPresenter
    private var hourlyForecastAdapter = HourlyForecastAdapter()
    @Inject lateinit var api: WeatherAPI
    @Inject lateinit var database: WeatherDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing presenter
        presenter = MainActivityPresenter(api, database)

        invokePresenterToCallApiForCurrentWeather()

        //getting hourly forecast
        invokePresenterToCallApiForHourlyForecast()

        setUpRecyclerView()

        binding.llNextForecast.setOnClickListener {
            val intent = Intent(this, Next7DayForecastActivity::class.java)
            startActivity(intent)
        }

        binding.ivSearch.setOnClickListener {
            presenter.callApiToGetWeatherUsingCityName(binding.etCityName.text.toString())
            hideKeyboard(binding.root)
        }

        getCurrentWeatherFromDatabase()

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

        //adding observer to get weather from searched city name
        presenter.cityResponse.observe(this, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvLocation.text = response.data?.name
                    binding.tvTemperature.text = response.data?.main?.temp.toString()
                    binding.tvDes.text = response.data?.weather?.get(0)?.main
                    binding.tvHumidity.text = response.data?.main?.humidity.toString()
                    binding.tvWind.text = response.data?.wind?.speed.toString()

                    when(response.data?.weather?.get(0)?.main?.lowercase()) {
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


                }
                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun invokePresenterToCallApiForCurrentWeather() {
        val latitude = SharedPreferences.getCurrentLatitude(this)
        val longitude = SharedPreferences.getCurrentLongitude(this)
        Log.d("TAGYOYO", "invokePresenterToCallApiForCurrentWeather: $latitude $longitude")
        if (!latitude.isNullOrBlank() && !longitude.isNullOrBlank()) {
            presenter.callApiToGetCurrentWeatherUsingLatLng(latitude, longitude)
        } else {
            Snackbar.make(binding.root,"Current location latitude longitude not found", Snackbar.LENGTH_SHORT).show()
            binding.tvTodayText.visibility = View.GONE
            binding.llNextForecast.visibility = View.GONE
        }
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

    @SuppressLint("CheckResult")
    private fun getCurrentWeatherFromDatabase() {
        //adding observer on database weather data
        presenter.getWeatherFromDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weathers ->
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
                    Log.d("TAGYOYO", "DATABASE: ${error.localizedMessage}")
                }
            )
    }

    override fun onResume() {
        super.onResume()
        getCurrentWeatherFromDatabase()
    }

    private fun setUpRecyclerView() {
        binding.rvHourlyForecast.apply {
            adapter = hourlyForecastAdapter
            layoutManager = LinearLayoutManager(this@MainActivity , LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onActivityDestroy()
        _binding = null
    }
}