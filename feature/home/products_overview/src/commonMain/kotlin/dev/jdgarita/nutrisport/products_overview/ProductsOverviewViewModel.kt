package dev.jdgarita.nutrisport.products_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jdgarita.nutrisport.data.domain.ProductRepository
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ProductsOverviewViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    val products = productRepository.readNewAndDiscountedProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading

        )
}