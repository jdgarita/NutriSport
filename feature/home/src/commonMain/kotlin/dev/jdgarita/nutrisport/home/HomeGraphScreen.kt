package dev.jdgarita.nutrisport.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jdgarita.nutrisport.home.component.BottomBar
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.navigation.Screen

@Composable
fun HomeGraphScreen() {
    val navigationController = rememberNavController()

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
                    selected = false,
                    onSelect = { /* Handle selection */ }
                )
            }
        }
    }
}