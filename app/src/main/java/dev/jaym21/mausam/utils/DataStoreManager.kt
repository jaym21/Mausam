package dev.jaym21.mausam.utils

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey

class DataStoreManager(val context: Context) {

    companion object {
        private val LATITUDE = stringPreferencesKey("LATITUDE")
        private val LONGITUDE = stringPreferencesKey("LONGITUDE")
    }
}