package dev.jdgarita.nutrisport.shared.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.IconWhite
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.SurfaceLighter
import dev.jdgarita.nutrisport.shared.SurfaceSecondary
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.domain.Country
import org.jetbrains.compose.resources.painterResource

@Composable
fun CountryPickerDialog(
    onDismissRequest: () -> Unit,
    onCountrySelected: (String) -> Unit
) {

}

@Composable
fun CountryPicker(
    modifier: Modifier = Modifier,
    country: Country,
    isSelected: Boolean = false,
    onSelected: (Country) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelected(country) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape),
            painter = painterResource(country.flag),
            contentDescription = "Country flag",
            alignment = Alignment.Center,
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = "+${country.dialCode} (${country.name}) ",
            fontSize = FontSize.SMALL,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = TextPrimary
        )
        Selector(
            isSelected = isSelected
        )
    }
}

@Composable
private fun Selector(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val animateBackground by animateColorAsState(
        targetValue = when {
            isSelected -> SurfaceSecondary
            else -> SurfaceLighter
        }
    )
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(animateBackground),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isSelected
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Checkmark),
                contentDescription = "Checkmark icon",
                tint = IconWhite
            )
        }
    }
}