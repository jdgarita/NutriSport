package dev.jdgarita.nutrisport.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.auth.component.GoogleButton
import dev.jdgarita.nutrisport.shared.Alpha
import dev.jdgarita.nutrisport.shared.BebasNeueFont
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.TextSecondary
import rememberMessageBarState

@Composable
fun AuthScreen() {
    val messageBarState = rememberMessageBarState()
    Scaffold { padding ->
        ContentWithMessageBar(
            contentBackgroundColor = Surface,
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            messageBarState = messageBarState,
            errorMaxLines = 2,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "NUTRISPORT",
                        textAlign = TextAlign.Center,
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.EXTRA_LARGE,
                        color = TextSecondary
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(Alpha.HALF),
                        text = "Sign in to continue",
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.EXTRA_REGULAR,
                        color = TextPrimary
                    )
                }
                GoogleButton(
                    loading = false,
                    onClick = {}
                )
            }
        }
    }
}