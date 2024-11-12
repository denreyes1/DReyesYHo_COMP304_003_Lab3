package com.dreyesyho.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable

@TypeConverters(WeatherTypeConverters::class)
@Entity(tableName = "Weather")
@Serializable
data class WeatherEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val dt: Long,
    val base: String,
    val cod: Int,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean = false
)
