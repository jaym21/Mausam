package dev.jaym21.mausam.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity: WeatherEntity)

    @Query("DELETE FROM weather_table")
    fun deleteAllWeather()

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): Flowable<List<WeatherEntity>>
}