package dev.jdgarita.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jdgarita.nutrisport.auth.AuthScreen
import dev.jdgarita.nutrisport.home.HomeGraphScreen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Screen.Auth> {
            AuthScreen()
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen()
        }
    }
}