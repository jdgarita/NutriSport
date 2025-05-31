package dev.jdgarita.nutrisport.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.component.PrimaryButton

@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        PrimaryButton(text = "Continue", icon = Resources.Icon.Person, enabled = true) {
            // Handle button click
        }
    }
}