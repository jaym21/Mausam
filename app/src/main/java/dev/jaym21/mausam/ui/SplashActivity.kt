package dev.jaym21.mausam.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.*
import dev.jaym21.mausam.utils.Constants
import dev.jaym21.mausam.utils.SharedPreferences
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //checking if location permissions are granted
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    val location = it.result
                    if (location != null) {
                        SharedPreferences.setCurrentLatitude(this, location.latitude.toString())
                        SharedPreferences.setCurrentLongitude(this, location.longitude.toString())
                        val intent = Intent(this , MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        //if the location is null, we will get new location of user
                        newLocationData()
                    }
                }
            } else {
                val gpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(gpsIntent)
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.locationPermissionCodeFine)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), Constants.locationPermissionCodeCoarse)
        }
    }

    @SuppressLint("MissingPermission")
    private fun newLocationData() {
        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(0)
            .setFastestInterval(0)
            .setNumUpdates(1)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            getLocation()
        }
    }

    //to check if location is enabled on device
    private fun isLocationEnabled(): Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //to check is location permissions are granted
    private fun checkPermission(): Boolean{
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.locationPermissionCodeCoarse) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getLocation()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == Constants.locationPermissionCodeFine) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}