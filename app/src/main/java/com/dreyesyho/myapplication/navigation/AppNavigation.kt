package com.dreyesyho.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dreyesyho.myapplication.views.FavoriteWeathersScreen
import com.dreyesyho.myapplication.views.WeatherDetailsScreen
import com.dreyesyho.myapplication.views.WeathersScreen

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
            WeathersScreen()

        }

        composable(Screens.WeatherDetailsScreen.route) {
            WeatherDetailsScreen()

        }

        composable(Screens.FavoriteWeathersScreen.route) {
            FavoriteWeathersScreen()

        }
    }
}