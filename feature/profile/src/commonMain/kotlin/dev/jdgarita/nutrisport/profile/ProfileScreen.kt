package dev.jdgarita.nutrisport.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jdgarita.nutrisport.shared.component.ProfileForm

@Composable
fun ProfileScreen() {
    ProfileForm(
        modifier = Modifier,
        firstName = "",
        onFirstNameChange = {},
        lastName = "",
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