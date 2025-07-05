package com.kyi.knowyouringredients.di

import com.kyi.knowyouringredients.core.data.networking.HttpClientFactory
import com.kyi.knowyouringredients.ingredients.data.networking.RemoteProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.ProductRepository
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(imageModule)
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteProductDataSource) // Define RemoteProductDataSource as a singleton
    single<ProductDataSource> { // Replace direct binding with repository
        ProductRepository(
            remoteDataSource = get(), // Inject RemoteProductDataSource
            localDataSource = null    // No local source yet
        )
    }
    viewModelOf(::ProductListViewModel)
}