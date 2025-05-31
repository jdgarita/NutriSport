package dev.jdgarita.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import dev.jdgarita.nutrisport.shared.domain.Customer
import dev.jdgarita.nutrisport.shared.util.RequestState

class CustomerRepositoryImpl : CustomerRepository {

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = try {
        if (user != null) {
            val customerCollection = Firebase.firestore.collection(collectionPath = "customer")
            val customer = Customer(
                id = user.uid,
                firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown",
                lastName = user.displayName?.split(" ")?.lastOrNull() ?: "Unknown",
                email = user.email ?: "Unknown",
                city = null,
                postalCode = null,
                address = null,
                phoneNumber = null,
                cart = emptyList(),
                isAdmin = false
            )

            val customerExists = customerCollection.document(user.uid).get().exists

            when {
                customerExists -> onSuccess()
                else -> {
                    customerCollection.document(user.uid).set(customer)
                    onSuccess()
                }
            }

        } else {
            onError("User is not available.")
        }
    } catch (e: Exception) {
        onError("Error while creating a Customer: ${e.message ?: "Unknown error"}")
    }

    override fun getCurrentUserId() = Firebase.auth.currentUser?.uid

    override suspend fun signOut() = try {
        Firebase.auth.signOut()
        RequestState.Success(Unit)
    } catch (e: Exception) {
        RequestState.Error("Error signing out: ${e.message ?: "Unknown error"}")
    }
}