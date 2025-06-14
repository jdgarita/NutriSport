package dev.jdgarita.nutrisport.data.domain

import dev.jdgarita.nutrisport.shared.domain.Order

interface OrderRepository {
    fun getCurrentUserId(): String?
    suspend fun createTheOrder(
        order: Order,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}