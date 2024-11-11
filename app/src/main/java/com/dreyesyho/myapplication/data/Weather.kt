package com.dreyesyho.myapplication.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("_id")
    val id: String,
)