package dev.jdgarita.nutrisport.products_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jdgarita.nutrisport.data.domain.ProductRepository
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProductsOverviewViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    val products = combine(
        productRepository.readNewProducts(),
        productRepository.readDiscountedProducts()
    ) { new, discounted ->
        when {
            new is RequestState.Success && discounted is RequestState.Success -> {
                RequestState.Success(
                    new.getSuccessData() + discounted.getSuccessData()
                )
            }

            new.isError() -> new
            discounted.isError() -> discounted
            else -> RequestState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )
}