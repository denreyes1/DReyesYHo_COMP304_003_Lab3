package com.dreyesyho.myapplication.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeathersAPI {
    @GET("weather")
    suspend fun fetchWeather(
        @Query("q") location: String,
        @Query("appid") key: String,
    ): Response<WeatherResponse>
}