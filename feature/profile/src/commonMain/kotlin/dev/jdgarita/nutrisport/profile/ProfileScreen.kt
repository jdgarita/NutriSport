package dev.jdgarita.nutrisport.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.BebasNeueFont
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.IconPrimary
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.component.PrimaryButton
import dev.jdgarita.nutrisport.shared.component.ProfileForm
import dev.jdgarita.nutrisport.shared.domain.Country
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
) {
    var country by remember { mutableStateOf(Country.Serbia) }

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Profile",
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.LARGE,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = "Back arrow icon",
                            tint = IconPrimary
                        )
                    }
                }
            )
        })
    { padding ->
        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding(),
                ).padding(
                    horizontal = 24.dp
                )
        ) {
            ProfileForm(
                modifier = Modifier.weight(1f),
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
                onPhoneNumberChange = {},
                country = country,
                onCountrySelect = { selectedCountry ->
                    country = selectedCountry
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrimaryButton(
                text = "Update",
                icon = Resources.Icon.Checkmark,
            ) {

            }
        }
    }
}