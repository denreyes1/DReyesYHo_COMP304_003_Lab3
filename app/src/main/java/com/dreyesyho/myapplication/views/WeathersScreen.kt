package com.dreyesyho.myapplication.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dreyesyho.myapplication.data.WeatherResponse
import com.dreyesyho.myapplication.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeathersScreen(
    onItemClicked: (WeatherResponse) -> Unit
) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val weatherUIState by weatherViewModel.weatherUIState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    val cities = listOf("Toronto", "Calgary", "Vancouver", "Montreal", "Quebec", "Cebu", "Hong Kong", "Mexico City", "Manila")

    // Filter the list of cities based on the query
    val filteredCities = if (query.isEmpty()) {
        emptyList()
    } else {
        cities.filter { it.contains(query, ignoreCase = true) }
    }

    // Loading state when searching for weather
    val isLoading = weatherUIState.isLoading

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Weather",
            modifier = Modifier
                .padding(top = 44.dp, bottom = 12.dp, end = 24.dp, start = 24.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search for a city") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Icon",
                            modifier = Modifier.clickable { query = "" }
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        // Display search results
        if (filteredCities.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            ) {
                items(filteredCities) { city ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Fill the entire width
                            .clickable {
                                weatherViewModel.getWeatherRemote(city) // Fetch weather data
                            }
                            .padding(horizontal = 24.dp, vertical = 4.dp) // Padding around the item
                    ) {
                        Text(
                            text = city,
                            modifier = Modifier
                                .align(Alignment.CenterStart) // Align text to the start
                                .padding(vertical = 12.dp), // Padding at the top
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Show loading indicator while fetching weather data
        AnimatedVisibility(
            visible = isLoading
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
        }

        // Show weather data once it's fetched
        AnimatedVisibility(
            visible = weatherUIState.weather.isNotEmpty()
        ) {
            if (query.isEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp)
                ) {
                    items(weatherUIState.weather) { weather ->
                        WeatherItem(
                            weather,
                            onItemClicked = onItemClicked
                        )
                    }
                }
            }
        }

        // Show error message if any error occurred
        AnimatedVisibility(
            visible = weatherUIState.error != null
        ) {
            Text(text = weatherUIState.error ?: "")
        }

        if (weatherUIState.query != null) {
            Log.i("DENSHO", "searched item: "+weatherUIState.query)
            onItemClicked(weatherUIState.query!!)
            weatherUIState.query = null
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeathersScreenPreview() {
}
