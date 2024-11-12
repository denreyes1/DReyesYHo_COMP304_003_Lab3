package com.dreyesyho.myapplication.views

import com.dreyesyho.myapplication.data.WeatherResponse

data class WeathersUIState(
    val isLoading: Boolean = false,
    val weather: List<WeatherResponse> = ArrayList(),
    val error: String? = null
)