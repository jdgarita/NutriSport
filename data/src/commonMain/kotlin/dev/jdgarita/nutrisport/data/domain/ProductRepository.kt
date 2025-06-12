package dev.jdgarita.nutrisport.data.domain

import dev.jdgarita.nutrisport.shared.domain.Product
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getCurrentUserId(): String?

    fun readNewAndDiscountedProducts(): Flow<RequestState<List<Product>>>
}