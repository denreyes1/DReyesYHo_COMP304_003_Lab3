package com.dreyesyho.myapplication.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListWeather(modifier: Modifier) {
    LazyColumn (
        modifier = modifier
    ) {
        items(getMockWeather()) { weather ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = weather)
            }
        }
    }
}

fun getMockWeather(): List<String> {
    val items = ArrayList<String>()
    items.add("Toronto")
    items.add("Vancouver")
    items.add("Calgary")
    return items
}

@Preview(showBackground = true)
@Composable
fun previewListWeather() {
    ListWeather(Modifier)
}