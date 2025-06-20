package dev.jdgarita.nutrisport.di

import dev.jdgarita.nutrisport.admin_panel.AdminPanelViewModel
import dev.jdgarita.nutrisport.auth.AuthViewModel
import dev.jdgarita.nutrisport.cart.CartViewModel
import dev.jdgarita.nutrisport.category_search.CategorySearchViewModel
import dev.jdgarita.nutrisport.checkout.CheckoutViewModel
import dev.jdgarita.nutrisport.checkout.domain.PaypalApi
import dev.jdgarita.nutrisport.data.AdminRepositoryImpl
import dev.jdgarita.nutrisport.data.CustomerRepositoryImpl
import dev.jdgarita.nutrisport.data.OrderRepositoryImpl
import dev.jdgarita.nutrisport.data.ProductRepositoryImpl
import dev.jdgarita.nutrisport.data.domain.AdminRepository
import dev.jdgarita.nutrisport.data.domain.CustomerRepository
import dev.jdgarita.nutrisport.data.domain.OrderRepository
import dev.jdgarita.nutrisport.data.domain.ProductRepository
import dev.jdgarita.nutrisport.details.DetailsViewModel
import dev.jdgarita.nutrisport.home.HomeGraphViewModel
import dev.jdgarita.nutrisport.manage_product.ManageProductViewModel
import dev.jdgarita.nutrisport.payment_completed.PaymentViewModel
import dev.jdgarita.nutrisport.products_overview.ProductsOverviewViewModel
import dev.jdgarita.nutrisport.profile.ProfileViewModel
import dev.jdgarita.nutrisport.shared.util.IntentHandler
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<PaypalApi> { PaypalApi() }
    single<IntentHandler> { IntentHandler() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::CategorySearchViewModel)
    viewModelOf(::CheckoutViewModel)
    viewModelOf(::PaymentViewModel)
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