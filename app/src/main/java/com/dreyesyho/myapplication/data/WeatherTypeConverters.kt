package com.dreyesyho.myapplication.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class WeatherTypeConverters {

    @TypeConverter
    fun fromWeatherList(value: List<Weather>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<Weather> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromCoord(value: Coord): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toCoord(value: String): Coord {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMain(value: Main): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toMain(value: String): Main {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromWind(value: Wind): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toWind(value: String): Wind {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromClouds(value: Clouds): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toClouds(value: String): Clouds {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromSys(value: Sys): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toSys(value: String): Sys {
        return Json.decodeFromString(value)
    }
}