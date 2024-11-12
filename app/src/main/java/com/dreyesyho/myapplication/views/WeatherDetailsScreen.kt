package com.dreyesyho.myapplication.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreyesyho.myapplication.capitalizeWords
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.data.getMockWeatherData
import com.dreyesyho.myapplication.data.getWeatherIcon
import com.dreyesyho.myapplication.data.isDaytime
import com.dreyesyho.myapplication.data.kelvinToCelsius
import com.dreyesyho.myapplication.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(onBackPressed: () -> Unit, weatherData: WeatherResponse) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    var data by remember { mutableStateOf(weatherData) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = data.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            WeatherScreen(paddingValues, data)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(
                        imageVector =
                        if (data.isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon"
                    )
                },
                text = {
                    Text(
                        if (data.isFavorite)
                            "Remove from favorites"
                        else
                            "Add to favorites")
                },
                onClick = {
                    // Toggle the favorite status
                    val updatedWeatherData = data.copy(isFavorite = !data.isFavorite)
                    data = updatedWeatherData

                    weatherViewModel.updateWeather(data)
                }
            )
        }
    )
}

@Composable
fun WeatherScreen(paddingValues: PaddingValues, weatherData: WeatherResponse) {
    Box(
        modifier = Modifier
            .fillMaxSize()
//                    .background(Color(0xFF007FFF)) // Blue background color
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val condition = weatherData.weather.get(0)
            // Weather Condition
            Text(
                text = condition.description.capitalizeWords(),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Temperature and Icon
            Row {
                Text(
                    text = "${kelvinToCelsius(weatherData.main.temp)}",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = getWeatherIcon(condition.main, isDaytime(weatherData))), // Replace with your moon icon resource
                    contentDescription = "Moon Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Feels Like Temperature
            Text(
                text = "Feels like ${kelvinToCelsius(weatherData.main.feelsLike)}°",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // High and Low Temperatures
            Text(
                text = "High ${kelvinToCelsius(weatherData.main.tempMax)}° · Low ${kelvinToCelsius(weatherData.main.tempMin)}°",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WeatherDetailsScreenPreview(){
    val w1 = getMockWeatherData()[0]
    WeatherDetailsScreen({}, w1)
}