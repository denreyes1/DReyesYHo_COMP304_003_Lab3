package com.dreyesyho.myapplication.data

interface WeathersRepository {
    suspend fun getWeather(): NetworkResult<WeatherResponse>
}