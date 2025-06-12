package dev.jdgarita.nutrisport.products_overview

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductsOverviewScreen() {
    val viewModel = koinViewModel<ProductsOverviewViewModel>()
}