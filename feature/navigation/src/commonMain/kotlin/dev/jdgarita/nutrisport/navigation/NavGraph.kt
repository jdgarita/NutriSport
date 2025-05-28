package dev.jdgarita.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jdgarita.nutrisport.auth.AuthScreen
import dev.jdgarita.nutrisport.home.HomeGraphScreen

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Auth,
    ) {
        composable<Screen.Auth> {
            AuthScreen()
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen()
        }
    }
}