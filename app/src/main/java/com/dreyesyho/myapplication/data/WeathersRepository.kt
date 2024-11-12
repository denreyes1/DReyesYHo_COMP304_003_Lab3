package com.dreyesyho.myapplication.data

import kotlinx.coroutines.flow.Flow

interface WeathersRepository {
    suspend fun getWeathers(): Flow<List<WeatherResponse>>
    suspend fun fetchRemoteWeathers()
    suspend fun fetchRemoteWeather(location: String)
    suspend fun updateWeather(weather: WeatherResponse)
    suspend fun getFavoriteWeather() : Flow<List<WeatherResponse>>
}