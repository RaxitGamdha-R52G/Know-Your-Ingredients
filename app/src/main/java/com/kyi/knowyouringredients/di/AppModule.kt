package com.kyi.knowyouringredients.di

import com.kyi.knowyouringredients.core.data.networking.HttpClientFactory
import com.kyi.knowyouringredients.ingredients.data.networking.RemoteProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.ProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.ProductRepository
import com.kyi.knowyouringredients.viewmodels.ProductListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(imageModule)
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteProductDataSource)
    single<ProductDataSource> {
        ProductRepository(
            remoteDataSource = get(),
            localDataSource = null
        )
    }
    viewModelOf(::ProductListViewModel)
}