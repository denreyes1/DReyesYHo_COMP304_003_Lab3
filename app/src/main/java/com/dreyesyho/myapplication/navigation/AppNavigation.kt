package com.dreyesyho.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dreyesyho.myapplication.views.FavoriteWeathersScreen
import com.dreyesyho.myapplication.views.WeatherDetailsScreen
import com.dreyesyho.myapplication.views.WeathersScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navHostController,
        startDestination = Screens.WeathersScreen.route
    ) {
        composable(Screens.WeathersScreen.route) {
            WeathersScreen(onItemClicked = { weatherData ->
                navHostController.navigate("${Screens.WeatherDetailsScreen.route}/${Json.encodeToString(weatherData)}")
            })

        }

        composable(
            route = "${Screens.WeatherDetailsScreen.route}/{weatherData}",
            arguments = listOf(
                navArgument("weatherData") {
                    type = NavType.StringType
                }
            )
        ) {
            WeatherDetailsScreen(
                onBackPressed = {
                    navHostController.popBackStack()
                },
                weatherData = Json.decodeFromString(it.arguments?.getString("weatherData") ?: "")
            )

        }

        composable(Screens.FavoriteWeathersScreen.route) {
            FavoriteWeathersScreen(
                onWeatherClicked = { weather ->
                    navHostController.navigate(
                        "${Screens.WeatherDetailsScreen.route}/${Json.encodeToString(weather)}"
                    )
                }
            )
        }
    }
}