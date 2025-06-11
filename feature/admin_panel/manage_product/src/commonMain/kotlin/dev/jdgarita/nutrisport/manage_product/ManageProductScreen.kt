package dev.jdgarita.nutrisport.manage_product

import ContentWithMessageBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.jdgarita.nutrisport.manage_product.util.PhotoPicker
import dev.jdgarita.nutrisport.shared.BebasNeueFont
import dev.jdgarita.nutrisport.shared.BorderIdle
import dev.jdgarita.nutrisport.shared.ButtonPrimary
import dev.jdgarita.nutrisport.shared.FontSize
import dev.jdgarita.nutrisport.shared.IconPrimary
import dev.jdgarita.nutrisport.shared.Resources
import dev.jdgarita.nutrisport.shared.Surface
import dev.jdgarita.nutrisport.shared.SurfaceLighter
import dev.jdgarita.nutrisport.shared.TextPrimary
import dev.jdgarita.nutrisport.shared.TextSecondary
import dev.jdgarita.nutrisport.shared.component.AlertTextField
import dev.jdgarita.nutrisport.shared.component.CustomTextField
import dev.jdgarita.nutrisport.shared.component.ErrorCard
import dev.jdgarita.nutrisport.shared.component.LoadingCard
import dev.jdgarita.nutrisport.shared.component.PrimaryButton
import dev.jdgarita.nutrisport.shared.component.dialog.CategoriesDialog
import dev.jdgarita.nutrisport.shared.domain.ProductCategory
import dev.jdgarita.nutrisport.shared.util.DisplayResult
import dev.jdgarita.nutrisport.shared.util.RequestState
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    id: String?,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<ManageProductViewModel>()
    val messageBarState = rememberMessageBarState()
    val screenState = viewModel.screenState
    val isFormValid = viewModel.isFormValid
    val thumbnailUploaderState = viewModel.thumbnailUploaderState
    var showCategoriesDialog by remember { mutableStateOf(false) }
    var dropDownMenuOpened by remember { mutableStateOf(false) }

    val photoPicker = koinInject<PhotoPicker>()

    photoPicker.InitializePhotoPicker { file ->
        viewModel.uploadThumbnailToStorage(
            file = file,
            onSuccess = {
                messageBarState.addSuccess("Image successfully uploaded!")
            }
        )
    }

    AnimatedVisibility(
        visible = showCategoriesDialog
    ) {
        CategoriesDialog(
            category = screenState.category,
            onDismiss = { showCategoriesDialog = false },
            onConfirmClick = { selectedCategory ->
                viewModel.updateCategory(selectedCategory)
                showCategoriesDialog = false
            }
        )
    }

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (id == null) "New Product"
                        else "Edit Product",
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.LARGE,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = "Back arrow icon",
                            tint = IconPrimary
                        )
                    }
                },
                actions = {
                   id.takeIf { it != null }?.let {
                       Box(modifier = Modifier) {
                           IconButton(onClick = { dropDownMenuOpened = true }) {
                               Icon(
                                   painter = painterResource(Resources.Icon.VerticalMenu),
                                   contentDescription = "Vertical Menu Icon",
                                   tint = IconPrimary
                               )
                           }
                           DropdownMenu(
                               containerColor = Surface,
                               expanded = dropDownMenuOpened,
                               onDismissRequest = {
                                   dropDownMenuOpened = false
                               }
                           ) {
                               DropdownMenuItem(
                                   leadingIcon = {
                                       Icon(
                                           modifier = Modifier.size(14.dp),
                                           painter = painterResource(Resources.Icon.Delete),
                                           contentDescription = "Delete Icon",
                                           tint = IconPrimary
                                       )
                                   },
                                   onClick = {
                                       dropDownMenuOpened = false
                                       viewModel.deleteProduct(
                                           onSuccess = navigateBack,
                                           onError = { errorMessage ->
                                               messageBarState.addError(errorMessage)
                                           }
                                       )
                                   },
                                   text = { Text(text = "Delete", color = TextPrimary) }
                               )
                           }
                       }
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
    )
    { padding ->
        ContentWithMessageBar(
            modifier = Modifier.fillMaxSize().padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            messageBarState = messageBarState,
            errorMaxLines = 2,
            contentBackgroundColor = Surface
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .padding(top = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = BorderIdle,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(SurfaceLighter)
                            .clickable(
                                enabled = thumbnailUploaderState is RequestState.Idle
                            ) {
                                photoPicker.open()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        thumbnailUploaderState.DisplayResult(
                            onIdle = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Resources.Icon.Plus),
                                    contentDescription = "Plus icon",
                                    tint = IconPrimary
                                )
                            },
                            onLoading = {
                                LoadingCard(
                                    modifier = Modifier.fillMaxSize()
                                )
                            },
                            onError = { errorMessage ->
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ErrorCard(message = errorMessage)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TextButton(
                                        onClick = {
                                            viewModel.updateThumbnailUploaderState(
                                                RequestState.Idle
                                            )
                                        }, colors = ButtonDefaults.textButtonColors(
                                            containerColor = Color.Transparent,
                                            contentColor = TextSecondary
                                        )
                                    ) {
                                        Text(
                                            text = "Try again",
                                            fontSize = FontSize.SMALL,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            },
                            onSuccess = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.TopEnd
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = ImageRequest.Builder(
                                            LocalPlatformContext.current
                                        ).data(screenState.thumbnail)
                                            .crossfade(enable = true).build(),
                                        contentDescription = "Product thumbnail",
                                        contentScale = ContentScale.Crop
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .clickable {
                                                viewModel.deleteThumbnailFromStorage(
                                                    onSuccess = {
                                                        messageBarState.addSuccess("Thumbnail removed successfully!")
                                                    },
                                                    onError = { message ->
                                                        messageBarState.addError(message)
                                                    }
                                                )
                                            }
                                            .padding(top = 12.dp, end = 12.dp)
                                            .background(color = ButtonPrimary)
                                            .padding(all = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(24.dp),
                                            painter = painterResource(Resources.Icon.Delete),
                                            contentDescription = "Delete icon"
                                        )
                                    }
                                }
                            },
                            transitionSpec = null,
                            backgroundColor = null

                        )
                    }
                    CustomTextField(
                        value = screenState.title,
                        onValueChange = viewModel::updateTitle,
                        placeholder = "Title",
                    )
                    CustomTextField(
                        modifier = Modifier.height(168.dp),
                        value = screenState.description,
                        onValueChange = viewModel::updateDescription,
                        placeholder = "Description",
                        expanded = true
                    )
                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.category.title,
                        onClick = { showCategoriesDialog = true }
                    )
                    AnimatedVisibility(
                        visible = screenState.category != ProductCategory.Accessories
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CustomTextField(
                                value = "${screenState.weight ?: ""}",
                                onValueChange = { viewModel.updateWeight(it.toIntOrNull() ?: 0) },
                                placeholder = "Weight",
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                )
                            )
                            CustomTextField(
                                value = screenState.flavors,
                                onValueChange = viewModel::updateFlavors,
                                placeholder = "Flavors",
                            )
                        }
                    }
                    CustomTextField(
                        value = screenState.price.toString(),
                        onValueChange = { value ->
                            if (value.isEmpty() || value.toDoubleOrNull() != null) {
                                viewModel.updatePrice(value.toDoubleOrNull() ?: 0.0)
                            }
                        },
                        placeholder = "price",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                PrimaryButton(
                    enabled = isFormValid,
                    text = if (id == null) "Add new product" else "Update",
                    icon = if (id == null) Resources.Icon.Plus else Resources.Icon.Checkmark,
                    onClick = {
                        if (id != null) {
                            viewModel.updateProduct(
                                onSuccess = {
                                    messageBarState.addSuccess("Product successfully updated!")
                                    navigateBack()
                                },
                                onError = { message -> messageBarState.addError(message) }
                            )
                        } else {
                            viewModel.createNewProduct(
                                onSuccess = {
                                    messageBarState.addSuccess("Product successfully added!")
                                    navigateBack()
                                },
                                onError = { message -> messageBarState.addError(message) }
                            )
                        }
                    }
                )
            }
        }
    }
}