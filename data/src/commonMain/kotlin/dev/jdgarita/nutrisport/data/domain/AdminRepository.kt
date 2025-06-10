package dev.jdgarita.nutrisport.data.domain

import dev.gitlive.firebase.storage.File
import dev.jdgarita.nutrisport.shared.domain.Product
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

interface AdminRepository {

    fun getCurrentUserId(): String?

    suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun uploadImageToStorage(file: File): String?

    suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun readLastTenProducts(): Flow<RequestState<List<Product>>>

    suspend fun readProductById(id: String) : RequestState<Product>
}