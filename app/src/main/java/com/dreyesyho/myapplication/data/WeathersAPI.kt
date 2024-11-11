package com.dreyesyho.myapplication.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeathersAPI {
    @GET("weathers")
    suspend fun fetchWeathers(
        @Query("tag") tag: String,
    ): Response<List<Weather>>
}