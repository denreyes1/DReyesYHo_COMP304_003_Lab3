package com.dreyesyho.myapplication.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteWeathersScreen(
    onWeatherClicked: (WeatherResponse) -> Unit
){
    val weathersViewModel: WeatherViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        weathersViewModel.getFavoriteWeathers()
    }
    val weathers by weathersViewModel.favoriteWeathers.collectAsStateWithLifecycle()

    if (weathers.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No favorite weather")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(weathers) { weather ->
                WeatherItem(
                    weatherData = weather,
                    onItemClicked = onWeatherClicked,
                    onFavoriteClicked = {
                        weathersViewModel.updateWeather(it)
                    }
                )
            }
        }
    }
}