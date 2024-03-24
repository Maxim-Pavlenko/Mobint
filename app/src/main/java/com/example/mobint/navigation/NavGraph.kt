package com.example.mobint.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobint.screens.home.HomeScreen
import com.example.mobint.screens.splash.AnimatedSpalshScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSpalshScreen(navController)
        }

        composable(route = Screen.Home.route) {
           HomeScreen()
        }
    }
}