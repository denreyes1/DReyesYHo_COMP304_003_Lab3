package com.dreyesyho.myapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeathersScreen(
    onItemClicked: (WeatherResponse) -> Unit,
) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val weatherUIState by weatherViewModel.weatherUIState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Weather",
            modifier = Modifier
                .padding(top = 44.dp, bottom = 12.dp, end = 24.dp, start = 24.dp)
                .fillMaxWidth(),
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )

        androidx.compose.animation.AnimatedVisibility(
            visible = weatherUIState.isLoading
        ) {
            CircularProgressIndicator()
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = weatherUIState.weather.isNotEmpty()
        ) {
            LazyColumn(Modifier.padding(16.dp)) {
                items(weatherUIState.weather) { weather ->
                    WeatherItem(
                        weather,
                        onItemClicked
                    )
                }
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = weatherUIState.error != null
        ) {
            Text(text = weatherUIState.error ?: "")
        }
    }
}