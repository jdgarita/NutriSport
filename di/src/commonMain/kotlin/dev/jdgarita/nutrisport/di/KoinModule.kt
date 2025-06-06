package dev.jdgarita.nutrisport.di

import dev.jdgarita.nutrisport.auth.AuthViewModel
import dev.jdgarita.nutrisport.data.AdminRepositoryImpl
import dev.jdgarita.nutrisport.data.CustomerRepositoryImpl
import dev.jdgarita.nutrisport.data.domain.AdminRepository
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import dev.jdgarita.nutrisport.home.HomeGraphViewModel
import dev.jdgarita.nutrisport.manage_product.ManageProductViewModel
import dev.jdgarita.nutrisport.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}