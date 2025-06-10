package dev.jdgarita.nutrisport.admin_panel

import androidx.lifecycle.ViewModel
import dev.jdgarita.nutrisport.data.domain.AdminRepository

class AdminPanelViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {}