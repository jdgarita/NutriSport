package dev.jdgarita.nutrisport.shared.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.Alpha
import dev.jdgarita.nutrisport.shared.ButtonDisabled
import dev.jdgarita.nutrisport.shared.ButtonPrimary
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(size = 6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonPrimary,
            contentColor = TextPrimary,
            disabledContainerColor = ButtonDisabled,
            disabledContentColor = TextPrimary.copy(alpha = Alpha.DISABLED)
        ),
        contentPadding = PaddingValues(all = 20.dp)
    ) {
        icon?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = "Button icon",
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Text(
            text = text,
            fontSize = FontSize.REGULAR,
            fontWeight = FontWeight.Medium
        )
    }
}