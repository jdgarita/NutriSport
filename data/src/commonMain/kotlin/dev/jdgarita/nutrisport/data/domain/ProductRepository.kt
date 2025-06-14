package dev.jdgarita.nutrisport.data.domain

import dev.jdgarita.nutrisport.shared.domain.Product
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getCurrentUserId(): String?

    fun readDiscountedProducts(): Flow<RequestState<List<Product>>>
    fun readNewProducts(): Flow<RequestState<List<Product>>>
    fun readProductByIdFlow(id: String): Flow<RequestState<Product>>
    fun readProductsByIdsFlow(ids: List<String>): Flow<RequestState<List<Product>>>
}