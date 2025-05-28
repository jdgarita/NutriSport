package dev.jdgarita.nutrisport.di

import dev.jdgarita.nutrisport.auth.AuthViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import dev.jdgarita.nutrisport.data.CustomerRepositoryImpl

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}