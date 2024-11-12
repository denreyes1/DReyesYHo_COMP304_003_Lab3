package com.dreyesyho.myapplication.data

import kotlinx.coroutines.flow.Flow

interface WeathersRepository {
    suspend fun getWeathers(): Flow<List<WeatherResponse>>
    suspend fun fetchRemoteWeather(location: String)
}