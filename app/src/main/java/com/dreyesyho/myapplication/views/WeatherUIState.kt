package com.dreyesyho.myapplication.views

import com.dreyesyho.myapplication.data.Weather

data class WeathersUIState(
    val isLoading: Boolean = false,
    val pets: List<Weather> = emptyList(),
    val error: String? = null
)