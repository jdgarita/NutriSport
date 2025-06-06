package dev.jdgarita.nutrisport.data.domain

import dev.jdgarita.nutrisport.shared.domain.Product

interface AdminRepository {

    fun getCurrentUserId(): String?

    suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}