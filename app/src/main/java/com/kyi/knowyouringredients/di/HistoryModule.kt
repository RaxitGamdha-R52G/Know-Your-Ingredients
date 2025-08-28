package com.kyi.knowyouringredients.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kyi.knowyouringredients.ingredients.data.history.FirestoreHistoryDataSource
import com.kyi.knowyouringredients.ingredients.data.repository.HistoryRepository
import com.kyi.knowyouringredients.ingredients.domain.repository.HistoryDataSource
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.HistoryViewModel
//import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {
    // Provide Firestore instance
    single { Firebase.firestore }

    // Provide DataSource and Repository
    single { FirestoreHistoryDataSource(get()) }
    single<HistoryDataSource> { HistoryRepository(get()) }

    // Provide ViewModel
    viewModel { HistoryViewModel(get(), get(), get()) }
}