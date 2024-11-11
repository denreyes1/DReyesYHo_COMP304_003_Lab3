package com.dreyesyho.myapplication.navigation

sealed class Screens(val route: String) {
    object WeathersScreen : Screens("weathers")
    object WeatherDetailsScreen : Screens("weatherDetails")
    object FavoriteWeathersScreen : Screens("favoriteWeathers")
}