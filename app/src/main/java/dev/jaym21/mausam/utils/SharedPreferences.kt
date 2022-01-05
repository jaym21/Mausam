package dev.jaym21.mausam.utils

import android.app.Activity
import android.content.Context

class SharedPreferences {

    companion object {

        fun setCurrentLatitude(context: Context, latitude: String) {
            val sharedPreferences = context.getSharedPreferences("Session", Activity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(Constants.CURRENT_LATITUDE, latitude)
            editor.apply()
        }

        fun getCurrentLatitude(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences("Session", Activity.MODE_PRIVATE)
            return sharedPreferences.getString(Constants.CURRENT_LATITUDE, "")
        }

        fun setCurrentLongitude(context: Context, longitude: String) {
            val sharedPreferences = context.getSharedPreferences("Session", Activity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(Constants.CURRENT_LONGITUDE, longitude)
            editor.apply()
        }

        fun getCurrentLongitude(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences("Session", Activity.MODE_PRIVATE)
            return sharedPreferences.getString(Constants.CURRENT_LONGITUDE, "")
        }

    }
}