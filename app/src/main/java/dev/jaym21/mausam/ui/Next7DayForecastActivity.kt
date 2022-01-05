package dev.jaym21.mausam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.mausam.databinding.ActivityNext7DayForecastBinding

class Next7DayForecastActivity : AppCompatActivity() {

    private var _binding: ActivityNext7DayForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNext7DayForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}