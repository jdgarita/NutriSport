package dev.jdgarita.nutrisport.data.domain

import dev.gitlive.firebase.auth.FirebaseUser
import dev.jdgarita.nutrisport.shared.util.RequestState

interface CustomerRepository {

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun getCurrentUserId(): String?

    suspend fun signOut() : RequestState<Unit>
}