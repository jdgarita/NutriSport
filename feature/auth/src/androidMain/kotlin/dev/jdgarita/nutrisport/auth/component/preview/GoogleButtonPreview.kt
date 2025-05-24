package dev.jdgarita.nutrisport.auth.component.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.auth.component.GoogleButton
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun GoogleButtonPreview() {
    var loading by remember { mutableStateOf(false) }
    GoogleButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        loading = loading,
        onClick = { loading = !loading }
    )
}