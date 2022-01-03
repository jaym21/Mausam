package dev.jaym21.mausam.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.jaym21.mausam.R
import dev.jaym21.mausam.data.local.WeatherDatabase
import dev.jaym21.mausam.data.remote.service.WeatherAPI
import dev.jaym21.mausam.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var presenter: MainActivityPresenter
    @Inject lateinit var api: WeatherAPI
    @Inject lateinit var database: WeatherDatabase

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing presenter
        presenter = MainActivityPresenter(api, database)

        invokePresenterToCallApi()

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
    }

    override fun invokePresenterToCallApi() {
        presenter.callApiToGetWeather("mumbai")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}