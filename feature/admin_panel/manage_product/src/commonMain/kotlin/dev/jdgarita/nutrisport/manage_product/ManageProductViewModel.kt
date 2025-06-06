package dev.jdgarita.nutrisport.manage_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.jdgarita.nutrisport.data.domain.AdminRepository
import dev.jdgarita.nutrisport.shared.domain.ProductCategory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ManageProductState(
    val id: String = Uuid.random().toHexString(),
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val category: String = ProductCategory.Protein.name,
    val flavors: List<String>? = null,
    val weight: Int? = null,
    val price: Double = 0.0
)

class ManageProductViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {
    var screenState by mutableStateOf(ManageProductState())
        private set

    fun updateId(value: String) {
        screenState = screenState.copy(id = value)
    }

    fun updateTitle(value: String) {
        screenState = screenState.copy(title = value)
    }

    fun updateDescription(value: String) {
        screenState = screenState.copy(description = value)
    }

    fun updateThumbnail(value: String) {
        screenState = screenState.copy(thumbnail = value)

    }

    fun updateCategory(value: ProductCategory) {
        screenState = screenState.copy(category = value.name)
    }

    fun updateFlavors(value: List<String>) {
        screenState = screenState.copy(flavors = value)
    }

    fun updateWeight(value: Int?) {
        screenState = screenState.copy(weight = value)
    }

    fun updatePrice(value: Double) {
        screenState = screenState.copy(price = value)
    }
}