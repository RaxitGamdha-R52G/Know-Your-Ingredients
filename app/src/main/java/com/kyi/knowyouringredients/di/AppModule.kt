package com.kyi.knowyouringredients.di

import com.kyi.knowyouringredients.core.data.networking.HttpClientFactory
import com.kyi.knowyouringredients.ingredients.data.networking.RemoteProductDataSource
import com.kyi.knowyouringredients.ingredients.domain.repository.ProductDataSource
import com.kyi.knowyouringredients.ingredients.data.repository.ProductRepository
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.ScanViewModel
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.SearchViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
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
    viewModel { ScanViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
}


