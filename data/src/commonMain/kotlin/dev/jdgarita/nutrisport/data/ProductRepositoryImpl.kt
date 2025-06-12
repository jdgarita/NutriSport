package dev.jdgarita.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.jdgarita.nutrisport.data.domain.ProductRepository
import dev.jdgarita.nutrisport.shared.domain.Product
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class ProductRepositoryImpl : ProductRepository {

    override fun getCurrentUserId(): String? = Firebase.auth.currentUser?.uid

    override fun readNewAndDiscountedProducts(): Flow<RequestState<List<Product>>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore

                database.collection(collectionPath = "product")
                    .where { "isNew" equalTo true }
                    .where { "isDiscounted" equalTo true }
                    .snapshots
                    .collectLatest { query ->
                        val products = query.documents.map { productDocument ->
                            Product(
                                id = productDocument.id,
                                title = productDocument.get(field = "title"),
                                createdAt = productDocument.get(field = "createdAt"),
                                description = productDocument.get(field = "description"),
                                thumbnail = productDocument.get(field = "thumbnail"),
                                category = productDocument.get(field = "category"),
                                flavors = productDocument.get(field = "flavors"),
                                weight = productDocument.get(field = "weight"),
                                price = productDocument.get(field = "price"),
                                isPopular = productDocument.get(field = "isPopular"),
                                isDiscounted = productDocument.get(field = "isDiscounted"),
                                isNew = productDocument.get(field = "isNew")
                            )
                        }
                        send(
                            RequestState.Success(
                                products.map { product ->
                                    product.copy(product.title.uppercase())
                                }
                            )
                        )
                    }
            } else {
                send(RequestState.Error("User is not available"))
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while searching products: ${e.message}"))
        }
    }
}