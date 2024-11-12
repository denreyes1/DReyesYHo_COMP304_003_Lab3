package com.dreyesyho.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.dreyesyho.myapplication.capitalizeWords
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.data.getMockWeatherData
import com.dreyesyho.myapplication.data.getWeatherIcon
import com.dreyesyho.myapplication.data.isDaytime
import com.dreyesyho.myapplication.data.kelvinToCelsius

@Composable
fun WeatherItem(weatherData: WeatherResponse, onItemClicked: (WeatherResponse) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clickable {
                onItemClicked(weatherData)
            },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFEDEDF4)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val condition = weatherData.weather.get(0)
            // Weather Icon
            Image(
                painter = painterResource(id = getWeatherIcon(condition.main, isDaytime(weatherData))), // Replace with your cloud icon resource
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
                    text = weatherData.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                // Weather Description
                Text(
                    text = condition.description.capitalizeWords(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Temperature
            Text(
                text = "${kelvinToCelsius(weatherData.main.temp)}°",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewListItemWeather() {
    val w1 = getMockWeatherData()[0]
    WeatherItem(w1, {})
}