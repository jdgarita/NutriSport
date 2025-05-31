package dev.jdgarita.nutrisport.profile

import androidx.compose.runtime.Composable
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.component.PrimaryButton

@Composable
fun ProfileScreen() {

    PrimaryButton(text = "Continue", icon = Resources.Icon.Person, enabled = true) {
        // Handle button click
    }
}