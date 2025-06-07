package dev.jdgarita.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import dev.jdgarita.nutrisport.data.domain.AdminRepository
import dev.jdgarita.nutrisport.shared.domain.Product
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

                productCollection.document(product.id).set(product)
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