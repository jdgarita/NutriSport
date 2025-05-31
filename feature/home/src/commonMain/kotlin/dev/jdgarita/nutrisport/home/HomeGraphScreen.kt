package dev.jdgarita.nutrisport.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.jdgarita.nutrisport.home.component.BottomBar
import dev.jdgarita.nutrisport.home.domain.BottomBarDestination
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.navigation.Screen

@Composable
fun HomeGraphScreen() {
    val navigationController = rememberNavController()
    val currentRoute = navigationController.currentBackStackEntryAsState()

    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.ProductOverView.screen.toString()) -> BottomBarDestination.ProductOverView
                route.contains(BottomBarDestination.Cart.screen.toString()) -> BottomBarDestination.Cart
                route.contains(BottomBarDestination.Categories.screen.toString()) -> BottomBarDestination.Categories
                else -> BottomBarDestination.ProductOverView
            }
        }
    }
    Scaffold(
        containerColor = Surface
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navigationController,
                startDestination = Screen.ProductOverview
            ) {
                composable<Screen.ProductOverview> {}
                composable<Screen.Cart> {}
                composable<Screen.Categories> {}
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier.padding(all = 12.dp)
            ) {
                BottomBar(
                    selected = selectedDestination,
                    onSelect = { destination ->
                        navigationController.navigate(destination.screen) {
                            launchSingleTop = true
                            popUpTo(Screen.ProductOverview) {
                                saveState = true
                                inclusive = false
                            }
                        }
                    }
                )
            }
        }
    }
}