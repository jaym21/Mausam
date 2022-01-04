package dev.jaym21.mausam.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    companion object {
        private val LATITUDE = stringPreferencesKey("LATITUDE")
        private val LONGITUDE = stringPreferencesKey("LONGITUDE")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE)
    }

    suspend fun saveLatitude(latitude: String) {
        context.dataStore.edit {
            it[LATITUDE] = latitude
        }
    }

    suspend fun saveLongitude(longitude: String) {
        context.dataStore.edit {
            it[LONGITUDE] = longitude
        }
    }

    val latitude: Flow<String>
        get() = context.dataStore.data.map {
            it[LATITUDE] ?: ""
        }

    val longitude: Flow<String>
        get() = context.dataStore.data.map {
            it[LONGITUDE] ?: ""
        }
}