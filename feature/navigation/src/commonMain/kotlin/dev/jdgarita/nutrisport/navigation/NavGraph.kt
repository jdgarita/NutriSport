package dev.jdgarita.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.jdgarita.nutrisport.admin_panel.AdminPanelScreen
import dev.jdgarita.nutrisport.auth.AuthScreen
import dev.jdgarita.nutrisport.category_search.CategorySearchScreen
import dev.jdgarita.nutrisport.checkout.CheckoutScreen
import dev.jdgarita.nutrisport.details.DetailsScreen
import dev.jdgarita.nutrisport.home.HomeGraphScreen
import dev.jdgarita.nutrisport.manage_product.ManageProductScreen
import dev.jdgarita.nutrisport.payment_completed.PaymentCompleted
import dev.jdgarita.nutrisport.profile.ProfileScreen
import dev.jdgarita.nutrisport.shared.domain.ProductCategory
import dev.jdgarita.nutrisport.shared.navigation.Screen
import dev.jdgarita.nutrisport.shared.util.IntentHandler
import org.koin.compose.koinInject

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {
    val navController = rememberNavController()

    val intentHandler = koinInject<IntentHandler>()
    val navigateTo by intentHandler.navigateTo.collectAsState()

    LaunchedEffect(navigateTo) {
        navigateTo?.let { paymentCompleted ->
            navController.navigate(paymentCompleted)
            intentHandler.resetNavigation()
        }
    }


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
                },
                navigateToDetails = { productId ->
                    navController.navigate(Screen.Details(id = productId))
                },
                navigateToCategorySearch = { category ->
                    navController.navigate(Screen.CategorySearch(category = category))
                },
                navigateToCheckout = { totalAmount ->
                    navController.navigate(Screen.Checkout(totalAmount = totalAmount))
                }
            )
        }

        composable<Screen.CategorySearch> {
            val category = ProductCategory.valueOf(it.toRoute<Screen.CategorySearch>().category)
            CategorySearchScreen(
                category = category,
                navigateToDetails = { id ->
                    navController.navigate(Screen.Details(id))
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen {
                navController.navigateUp()
            }
        }

        composable<Screen.AdminPanel> {
            AdminPanelScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToManageProduct = { id ->
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }

        composable<Screen.ManageProduct> {
            val id = it.toRoute<Screen.ManageProduct>().id
            ManageProductScreen(
                id = id,
            ) {
                navController.navigateUp()
            }
        }

        composable<Screen.Details> {
            DetailsScreen {
                navController.navigateUp()
            }
        }

        composable<Screen.Checkout> {
            val totalAmount = it.toRoute<Screen.Checkout>().totalAmount
            CheckoutScreen(
                totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToPaymentCompleted = { isSuccess, error ->
                    navController.navigate(Screen.PaymentCompleted(isSuccess, error))
                }
            )
        }

        composable<Screen.PaymentCompleted> {
            PaymentCompleted(
                navigateBack = {
                    navController.navigate(Screen.HomeGraph) {
                        launchSingleTop = true
                        // Clear backstack completely
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}