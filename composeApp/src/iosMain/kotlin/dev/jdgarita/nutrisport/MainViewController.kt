package dev.jdgarita.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import dev.jdgarita.nutrisport.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeKoin()
    }
) { App() }