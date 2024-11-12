package com.dreyesyho.myapplication.views

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreyesyho.myapplication.R
import com.dreyesyho.myapplication.capitalizeWords
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.data.getMockWeatherData
import com.dreyesyho.myapplication.data.getWeatherIcon
import com.dreyesyho.myapplication.data.isDaytime
import com.dreyesyho.myapplication.data.kelvinToCelsius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(onBackPressed: () -> Unit, weatherData: WeatherResponse) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = weatherData.name,
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
            WeatherScreen(paddingValues, weatherData)
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
fun WeatherScreenPreview(){
    val w1 = getMockWeatherData()[0]
    WeatherScreen(paddingValues = PaddingValues(), w1)
}