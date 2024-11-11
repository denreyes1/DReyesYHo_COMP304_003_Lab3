package com.dreyesyho.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.data.getMockWeatherData
import com.dreyesyho.myapplication.data.getWeatherIcon
import com.dreyesyho.myapplication.data.kelvinToCelsius

@Composable
fun ListWeather(modifier: Modifier) {
    LazyColumn (
        modifier = modifier
            .padding(top=16.dp),
    ) {
        items(getMockWeatherData()) { weather ->
            Row(
                modifier = Modifier
                    .padding(start=16.dp,end=16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherWidget(weather)
            }
        }
    }
}

@Composable
fun WeatherWidget(weather: WeatherResponse) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFEDEDF4)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val condition = weather.weather.get(0).main
            // Weather Icon
            Image(
                painter = painterResource(id = getWeatherIcon(condition)), // Replace with your cloud icon resource
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Location Name
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                // Weather Description
                Text(
                    text = condition,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Temperature
            Text(
                text = "${kelvinToCelsius(weather.main.temp)}°",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun previewListWeather() {
    val w1 = getMockWeatherData()[0]
    WeatherWidget(w1)
}