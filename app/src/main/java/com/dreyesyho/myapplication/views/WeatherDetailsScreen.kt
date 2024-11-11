package com.dreyesyho.myapplication.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dreyesyho.myapplication.data.WeatherResponse

@Composable
fun WeatherDetailsScreen(onBackPressed: () -> Unit, weatherData: WeatherResponse) {
    Text(text = "${weatherData.name}")
}