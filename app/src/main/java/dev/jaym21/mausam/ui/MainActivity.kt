package dev.jaym21.mausam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.mausam.R

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun invokePresenterToCallApi() {

    }
}