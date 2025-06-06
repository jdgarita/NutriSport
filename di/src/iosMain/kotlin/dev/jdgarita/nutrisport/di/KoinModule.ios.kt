package dev.jdgarita.nutrisport.di

import dev.jdgarita.nutrisport.manage_product.util.PhotoPicker
import org.koin.dsl.module

actual val targetModule = module {
    single<PhotoPicker> { PhotoPicker() }
}