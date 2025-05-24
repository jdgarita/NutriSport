package dev.jdgarita.nutrisport

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.jdgarita.nutrisport.navigation.SetupNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        SetupNavGraph()
    }
}