package com.kyi.knowyouringredients.di

import com.kyi.knowyouringredients.core.firebase.FirebaseAuthProvider
import com.kyi.knowyouringredients.ingredients.data.repository.AuthRepository
import com.kyi.knowyouringredients.ingredients.domain.repository.AuthSource
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {

    // Firebase Auth instance from singleton provider
    single { FirebaseAuthProvider.auth }.also { println("Providing FirebaseAuth")}

    // Provide AuthRepository as AuthSource
    single<AuthSource> {
        AuthRepository(
            firebaseAuth = get()
        )
    }.also { println("Providing AuthRepository") }

    // ViewModel for authentication logic
    viewModelOf(::AuthViewModel).also { println("Providing AuthViewModel") }
}