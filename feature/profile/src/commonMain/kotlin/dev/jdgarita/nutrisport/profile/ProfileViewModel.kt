package dev.jdgarita.nutrisport.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import dev.jdgarita.nutrisport.shared.domain.Country
import dev.jdgarita.nutrisport.shared.domain.PhoneNumber
import dev.jdgarita.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileScreenState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String? = null,
    val postalCode: Int? = null,
    val address: String? = null,
    val country: Country = Country.Serbia,
    val phoneNumber: PhoneNumber? = null
)

class ProfileViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val customer = customerRepository.readCustomerFlow()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

    var screenState: ProfileScreenState? by mutableStateOf(ProfileScreenState())
        private set

    init {
        viewModelScope.launch {
            customer.collectLatest { data ->
                if (data.isSuccess()) {
                    val fetchedCustomer = data.getSuccessData()
                    if (fetchedCustomer != null) {
                        screenState = ProfileScreenState(
                            firstName = fetchedCustomer.firstName,
                            lastName = fetchedCustomer.lastName,
                            email = fetchedCustomer.email,
                            city = fetchedCustomer.city,
                            postalCode = fetchedCustomer.postalCode,
                            address = fetchedCustomer.address,
                            phoneNumber = fetchedCustomer.phoneNumber,
                            country = Country.entries.firstOrNull {
                                it.dialCode == fetchedCustomer.phoneNumber?.dialCode
                            } ?: Country.Serbia
                        )
                    }
                } else if (data.isError()) {
                    screenState = null
                }
            }
        }
    }
}