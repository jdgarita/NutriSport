package dev.jdgarita.nutrisport.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import dev.jdgarita.nutrisport.data.domain.ProductRepository
import dev.jdgarita.nutrisport.shared.domain.CartItem
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val productRepository: ProductRepository,
    private val customerRepository: CustomerRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val product =
        productRepository.readProductByIdFlow(id = savedStateHandle.get<String>("id") ?: "")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RequestState.Loading
            )

    var quantity by mutableStateOf(1)
        private set

    var selectedFlavor: String? by mutableStateOf(null)
        private set

    fun updateQuantity(newValue: Int) {
        quantity = newValue
    }

    fun updateSelectedFlavor(newValue: String?) {
        selectedFlavor = newValue
    }

    fun addItemToCard(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val productId = savedStateHandle.get<String>("id")

            if (productId != null) {
                customerRepository.addItemToCart(
                    cartItem = CartItem(
                        productId = productId,
                        flavor = selectedFlavor,
                        quantity = quantity
                    ),
                    onSuccess = onSuccess,
                    onError = onError
                )
            } else {
                onError("Product id is not found.")
            }
        }
    }
}