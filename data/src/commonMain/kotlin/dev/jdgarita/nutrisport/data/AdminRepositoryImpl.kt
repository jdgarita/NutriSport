package dev.jdgarita.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import dev.jdgarita.nutrisport.data.domain.AdminRepository
import dev.jdgarita.nutrisport.shared.domain.Product
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withTimeout
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AdminRepositoryImpl : AdminRepository {
    override fun getCurrentUserId(): String? = Firebase.auth.currentUser?.uid

    override suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()
            if (currentUserId != null) {
                val firestore = Firebase.firestore
                val productCollection = firestore.collection(collectionPath = "product")

                productCollection.document(product.id).set(
                    product.copy(title = product.title.lowercase())
                )
                onSuccess()
            } else {
                onError("User is not available")
            }
        } catch (e: Exception) {
            onError("Error while creating a new product: ${e.message ?: "Unknown error"}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun uploadImageToStorage(file: File): String? =
        if (getCurrentUserId() != null) {
            val storage = Firebase.storage.reference
            val imagePath = storage.child(path = "images/${Uuid.random().toHexString()}")
            try {
                withTimeout(timeMillis = 20000L) {
                    imagePath.putFile(file)
                    imagePath.getDownloadUrl()
                }
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }

    override suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val storagePath = extractFirebaseStoragePath(downloadUrl)
            if (storagePath != null) {
                Firebase.storage.reference(storagePath).delete()
                onSuccess()
            } else {
                onError("Storage path is null")
            }
        } catch (e: Exception) {
            onError("Error while deleting a thumbnail: ${e.message ?: "Unknown error"}")
        }
    }

    override fun readLastTenProducts(): Flow<RequestState<List<Product>>> = channelFlow {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                database.collection(collectionPath = "product")
                    .orderBy(field = "createdAt", direction = Direction.DESCENDING)
                    .limit(limit = 10)
                    .snapshots
                    .collectLatest { query ->
                        val products = query.documents.map { document ->
                            Product(
                                id = document.id,
                                title = document.get(field = "title"),
                                createdAt = document.get(field = "createdAt"),
                                description = document.get(field = "description"),
                                thumbnail = document.get(field = "thumbnail"),
                                category = document.get(field = "category"),
                                flavors = document.get(field = "flavors"),
                                weight = document.get(field = "weight"),
                                price = document.get(field = "price"),
                                isPopular = document.get(field = "isPopular"),
                                isDiscounted = document.get(field = "isDiscounted"),
                                isNew = document.get(field = "isNew")
                            )
                        }
                        send(RequestState.Success(data = products.map { it.copy(title = it.title.uppercase()) }))
                    }
            } else {
                send(RequestState.Error("User is not available"))
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while reading the last ten items from the database: ${e.message ?: "Unknown error"}"))
        }
    }

    override suspend fun readProductById(id: String): RequestState<Product> {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productDocument =
                    database.collection(collectionPath = "product").document(id).get()
                if (productDocument.exists) {
                    val product = Product(
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
                    return RequestState.Success(data = product.copy(title = product.title.uppercase()))
                } else {
                    return RequestState.Error("Selected product not found.")
                }
            } else {
                return RequestState.Error("User is not available")
            }
        } catch (e: Exception) {
            return RequestState.Error("Error while reading a selected product: ${e.message ?: "Unknown error"}")
        }
    }

    override suspend fun updateProductThumbnail(
        productId: String,
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productCollection =
                    database.collection(collectionPath = "product")
                val existingProduct = productCollection.document(productId).get()
                if (existingProduct.exists) {
                    productCollection.document(productId).update("thumbnail" to downloadUrl)
                    onSuccess()
                } else {
                    onError("Selected product not found.")
                }
            } else {
                onError("User is not available")
            }

        } catch (e: Exception) {
            onError("Error while updating a thumbnail: ${e.message ?: "Unknown error"}")
        }
    }

    override suspend fun updateProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productCollection = database.collection(collectionPath = "product")
                val existingProduct = productCollection.document(product.id).get()

                if (existingProduct.exists) {
                    productCollection.document(product.id).update(
                        product.copy(title = product.title.lowercase())
                    )
                    onSuccess()
                } else {
                    onError("Selected product not found.")
                }
            } else {
                onError("User is not available")
            }

        } catch (e: Exception) {
            onError("Error while updating a product: ${e.message ?: "Unknown error"}")
        }
    }

    override suspend fun deleteProduct(
        productId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val productCollection = database.collection(collectionPath = "product")
                val existingProduct = productCollection.document(productId).get()

                if (existingProduct.exists) {
                    productCollection.document(productId).delete()
                    onSuccess()
                } else {
                    onError("Selected product not found.")
                }
            } else {
                onError("User is not available")
            }

        } catch (e: Exception) {
            onError("Error while deleting a product: ${e.message ?: "Unknown error"}")
        }
    }

    override fun searchProductsByTitle(searchQuery: String): Flow<RequestState<List<Product>>> =
        channelFlow {
            try {
                val userId = getCurrentUserId()
                if (userId != null) {
                    val database = Firebase.firestore

//                    val queryText = searchQuery.trim().lowercase()
//                    val endText = queryText + "\uf8ff"

                    database.collection(collectionPath = "product")
//                        .orderBy("title")
//                        .startAt(queryText)
//                        .endAt(endText)
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
                                    products
                                        .filter { it.title.contains(searchQuery) }
                                        .map { product ->
                                            product.copy(title = product.title.uppercase())
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

    private fun extractFirebaseStoragePath(downloadUrl: String): String? {
        val startIndex = downloadUrl.indexOf("/o/") + 3
        if (startIndex < 3) {
            return null
        }

        val endIndex = downloadUrl.indexOf("?", startIndex)
        val encodePath = if (endIndex != -1) {
            downloadUrl.substring(startIndex, endIndex)
        } else {
            downloadUrl.substring(startIndex)
        }
        return decodeFirebaseStoragePath(encodePath)
    }

    private fun decodeFirebaseStoragePath(encodePath: String): String {
        val decodedPath = encodePath
            .replace("%2F", "/")
            .replace("%20", " ")
        return decodedPath
    }


}