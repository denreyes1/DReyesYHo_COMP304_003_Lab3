package com.dreyesyho.myapplication.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.navigation.ContentType

@Composable
fun WeathersScreen(
    onItemClicked: (WeatherResponse) -> Unit
) {
    ListWeather(modifier = Modifier, onItemClicked = onItemClicked)
}