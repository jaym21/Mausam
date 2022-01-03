package dev.jaym21.mausam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.mausam.R
import dev.jaym21.mausam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
    }

    override fun invokePresenterToCallApi() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}