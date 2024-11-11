package com.dreyesyho.myapplication.data

interface WeathersRepository {
    suspend fun getWeather(location: String): NetworkResult<WeatherResponse>
}