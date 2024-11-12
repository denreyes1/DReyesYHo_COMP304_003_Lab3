package com.dreyesyho.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM Weather")
    fun getWeathers(): Flow<List<WeatherEntity>>

    @Update
    suspend fun update(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM Weather WHERE isFavorite = 1")
    fun getFavoriteWeathers() : Flow<List<WeatherEntity>>
}