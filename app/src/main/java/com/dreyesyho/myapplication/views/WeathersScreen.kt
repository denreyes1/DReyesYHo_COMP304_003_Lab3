package com.dreyesyho.myapplication.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dreyesyho.myapplication.data.WeatherResponse

@Composable
fun WeathersScreen(
    onItemClicked: (WeatherResponse) -> Unit,
) {
    ListWeather(modifier = Modifier, onItemClicked = onItemClicked)
}