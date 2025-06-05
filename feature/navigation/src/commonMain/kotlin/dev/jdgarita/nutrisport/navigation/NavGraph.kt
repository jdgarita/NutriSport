package dev.jdgarita.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.jdgarita.nutrisport.admin_panel.AdminPanelScreen
import dev.jdgarita.nutrisport.auth.AuthScreen
import dev.jdgarita.nutrisport.home.HomeGraphScreen
import dev.jdgarita.nutrisport.manage_product.ManageProductScreen
import dev.jdgarita.nutrisport.profile.ProfileScreen
import dev.jdgarita.nutrisport.shared.navigation.Screen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Screen.Auth> {
            AuthScreen {
                navController.navigate(Screen.HomeGraph) {
                    popUpTo(Screen.Auth) { inclusive = true }
                }
            }
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo(Screen.HomeGraph) { inclusive = true }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                },
                navigateToAdminPanel = {
                    navController.navigate(Screen.AdminPanel)
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen {
                navController.navigateUp()
            }
        }

        composable<Screen.AdminPanel> {
            AdminPanelScreen {
                navController.navigateUp()
            }
        }

        composable<Screen.ManageProduct> {
            val id = it.toRoute<Screen.ManageProduct>().id
            ManageProductScreen(
                id = id,
            ) {
                navController.navigateUp()
            }
        }
    }
}