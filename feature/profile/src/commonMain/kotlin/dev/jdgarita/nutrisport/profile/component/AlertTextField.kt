package dev.jdgarita.nutrisport.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.BorderIdle
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.SurfaceLighter
import dev.jdgarita.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlertTextField(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(SurfaceLighter)
            .border(
                width = 1.dp,
                color = BorderIdle,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Image(
                modifier = Modifier.size(14.dp),
                painter = painterResource(it),
                contentDescription = "Text field icon"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            fontSize = FontSize.REGULAR,
            color = TextPrimary,
            text = text
        )
    }
}