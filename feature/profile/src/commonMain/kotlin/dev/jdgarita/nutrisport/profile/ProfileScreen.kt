package dev.jdgarita.nutrisport.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.component.ProfileForm

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .background(Surface)
            .systemBarsPadding()
    ) {
        ProfileForm(
            modifier = Modifier,
            firstName = "Juan Diego",
            onFirstNameChange = {},
            lastName = "Garita Segura",
            onLastNameChange = {},
            email = "",
            city = "",
            onCityChange = {},
            postalCode = null,
            onPostalCodeChange = {},
            address = "",
            onAddressChange = {},
            phoneNumber = null,
            onPhoneNumberChange = {}
        )
    }
}