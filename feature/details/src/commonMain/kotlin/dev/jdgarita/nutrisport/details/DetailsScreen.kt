package dev.jdgarita.nutrisport.details

import ContentWithMessageBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.BebasNeueFont
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.IconPrimary
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.painterResource
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navigateBack: () -> Unit
) {
    val messageBarState = rememberMessageBarState()
    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Surface,
                    scrolledContainerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        }
    )
    { padding ->
        ContentWithMessageBar(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            messageBarState = messageBarState,
            errorMaxLines = 2,
            contentBackgroundColor = Surface
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(top = 12.dp)
                ) {
//                    AsyncImage(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                            .clip(RoundedCornerShape(size = 12.dp))
//                            .border(
//                                width = 1.dp,
//                                color = BorderIdle,
//                                shape = RoundedCornerShape(size = 12.dp)
//                            ),
//                        model = ImageRequest.Builder(LocalPlatformContext.current)
//                            .data(product.thumbnail)
//                            .crossfade(enable = true)
//                            .build(),
//                        contentDescription = "Product thumbnail",
//                        contentScale = ContentScale.Crop
//                    )
                    Spacer(modifier = Modifier.height(12.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        AnimatedContent(
//                            targetState = product.category,
//                        ) { category ->
//                            if (ProductCategory.valueOf(category) == ProductCategory.Accessories) {
//                                Spacer(modifier = Modifier.weight(1f))
//                            } else {
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Icon(
//                                        modifier = Modifier.size(14.dp),
//                                        painter = painterResource(Resources.Icon.Weight),
//                                        contentDescription = "Weight icon"
//                                    )
//                                    Spacer(modifier = Modifier.height(4.dp))
//                                    Text(
//                                        text = "${product.weight}g",
//                                        fontSize = FontSize.EXTRA_SMALL,
//                                        color = TextPrimary
//                                    )
//                                }
//                            }
//                        }
//                        Text(
//                            text = "$${product.price}",
//                            fontSize = FontSize.EXTRA_REGULAR,
//                            color = TextSecondary,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
                }
                Column { }
            }
        }
    }
}