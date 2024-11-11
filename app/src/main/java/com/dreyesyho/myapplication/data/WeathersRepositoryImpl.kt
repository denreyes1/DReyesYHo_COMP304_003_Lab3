package com.dreyesyho.myapplication.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeathersRepositoryImpl(
    private val weathersAPI: WeathersAPI,
    private val dispatcher: CoroutineDispatcher
): WeathersRepository {
    override suspend fun getPets(): NetworkResult<List<Weather>> {
        return withContext(dispatcher) {
            try {
                val response = weathersAPI.fetchWeathers("")
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