package dev.jdgarita.nutrisport.products_overview

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.Alpha
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.component.InfoCard
import dev.jdgarita.nutrisport.shared.component.LoadingCard
import dev.jdgarita.nutrisport.shared.component.ProductCard
import dev.jdgarita.nutrisport.shared.util.DisplayResult
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductsOverviewScreen() {
    val viewModel = koinViewModel<ProductsOverviewViewModel>()
    val products by viewModel.products.collectAsState()

    products.DisplayResult(
        onLoading = {
            LoadingCard(
                modifier = Modifier.fillMaxSize()
            )
        },
        onError = { message ->
            InfoCard(
                image = Resources.Image.Cat,
                title = "Oops!",
                subtitle = message
            )
        },
        onSuccess = { productList ->
            AnimatedContent(
                targetState = productList
            ) { products ->
                if (products.isNotEmpty()) {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            modifier = Modifier
                                .alpha(Alpha.HALF)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Discounted Products",
                            fontSize = FontSize.EXTRA_REGULAR,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = products
                                    .filter { it.isDiscounted }
                                    .sortedBy { it.createdAt }
                                    .take(3),
                                key = { it.id }
                            ) { product ->
                                ProductCard(
                                    modifier = Modifier,
                                    product = product,
                                    onClick = {

                                    }
                                )
                            }
                        }
                    }
                } else {
                    InfoCard(
                        image = Resources.Image.Cat,
                        title = "Nothing here!",
                        subtitle = "Empty products list"
                    )
                }
            }
        }
    )
}