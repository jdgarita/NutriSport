package dev.jdgarita.nutrisport.data.domain

import dev.gitlive.firebase.auth.FirebaseUser
import dev.jdgarita.nutrisport.shared.domain.CartItem
import dev.jdgarita.nutrisport.shared.domain.Customer
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun getCurrentUserId(): String?

    suspend fun signOut(): RequestState<Unit>

    fun readCustomerFlow(): Flow<RequestState<Customer>>

    suspend fun updateCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun addItemToCart(
        cartItem: CartItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun updateCartItemQuantity(
        id: String,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}