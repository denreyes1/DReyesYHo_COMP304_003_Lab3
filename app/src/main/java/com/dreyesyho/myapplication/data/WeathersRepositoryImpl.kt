package com.dreyesyho.myapplication.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

const val APIKEY = "ea378a6dfe032f00ce2f79987d602c7c"

class WeathersRepositoryImpl(
    private val weathersAPI: WeathersAPI,
    private val dispatcher: CoroutineDispatcher
): WeathersRepository {

    override suspend fun getWeather(location: String): NetworkResult<WeatherResponse> {
        return withContext(dispatcher) {
            try {
                val response = weathersAPI.fetchWeather(location, APIKEY)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

}