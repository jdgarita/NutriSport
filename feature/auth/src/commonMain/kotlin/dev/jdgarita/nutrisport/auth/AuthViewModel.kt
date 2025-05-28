package dev.jdgarita.nutrisport.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.auth.FirebaseUser
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class AuthViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.createCustomer(user, onSuccess, onError)
        }
    }
}