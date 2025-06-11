package dev.jdgarita.nutrisport.admin_panel

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jdgarita.nutrisport.shared.BebasNeueFont
import dev.jdgarita.nutrisport.shared.BorderIdle
import dev.jdgarita.nutrisport.shared.ButtonPrimary
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.IconPrimary
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.SurfaceLighter
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.component.InfoCard
import dev.jdgarita.nutrisport.shared.component.LoadingCard
import dev.jdgarita.nutrisport.shared.component.ProductCard
import dev.jdgarita.nutrisport.shared.util.DisplayResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    navigateBack: () -> Unit,
    navigateToManageProduct: (String?) -> Unit
) {
    val viewModel = koinViewModel<AdminPanelViewModel>()
    val products = viewModel.filteredProducts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var searchBarVisible by mutableStateOf(false)

    Scaffold(
        containerColor = Surface,
        topBar = {
            AnimatedContent(
                targetState = searchBarVisible
            ) { visible ->
                if (visible) {
                    SearchBar(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                        inputField = {
                            SearchBarDefaults.InputField(
                                modifier = Modifier.fillMaxWidth(),
                                query = searchQuery,
                                onQueryChange = viewModel::updateSearchQuery,
                                expanded = false,
                                onExpandedChange = {},
                                onSearch = {},
                                placeholder = {
                                    Text(
                                        text = "Search here",
                                        fontSize = FontSize.REGULAR,
                                        color = TextPrimary
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        modifier = Modifier.size(14.dp),
                                        onClick = {
                                            if (searchQuery.isNotEmpty()) {
                                                viewModel.updateSearchQuery("")
                                            } else {
                                                searchBarVisible = false
                                            }
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(Resources.Icon.Close),
                                            contentDescription = "Close icon",
                                            tint = IconPrimary
                                        )
                                    }
                                }
                            )
                        },
                        colors = SearchBarColors(
                            containerColor = SurfaceLighter,
                            dividerColor = BorderIdle
                        ),
                        expanded = false,
                        onExpandedChange = {},
                        content = {}
                    )
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Admin Panel",
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
                        actions = {
                            IconButton(
                                onClick = { searchBarVisible = true }
                            ) {
                                Icon(
                                    painter = painterResource(Resources.Icon.Search),
                                    contentDescription = "Search icon",
                                    tint = IconPrimary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Surface,
                            scrolledContainerColor = Surface,
                            navigationIconContentColor = IconPrimary,
                            titleContentColor = TextPrimary,
                            actionIconContentColor = IconPrimary
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToManageProduct(null) },
                containerColor = ButtonPrimary,
                contentColor = IconPrimary
            ) {
                Icon(
                    painter = painterResource(Resources.Icon.Plus),
                    contentDescription = "Add icon",
                    tint = IconPrimary
                )
            }
        }
    )
    { padding ->
        products.value.DisplayResult(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            onIdle = {},
            onLoading = {
                LoadingCard(
                    modifier = Modifier.fillMaxSize()
                )
            },
            onError = { errorMessage ->
                InfoCard(
                    image = Resources.Image.Cat,
                    title = "Oops",
                    subtitle = errorMessage
                )
            },
            onSuccess = { lastProducts ->
                AnimatedContent(
                    targetState = lastProducts
                ) { products ->
                    if (products.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = lastProducts,
                                key = { it.id }
                            ) { product ->
                                ProductCard(
                                    modifier = Modifier,
                                    product = product
                                ) { productId ->
                                    navigateToManageProduct(productId)
                                }
                            }
                        }
                    } else {
                        InfoCard(
                            image = Resources.Image.Cat,
                            title = "Oops!",
                            subtitle = "Products not found."
                        )
                    }
                }
            },
        )
    }
}