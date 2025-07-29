package com.kyi.knowyouringredients.ingredients.domain.repository

import com.kyi.knowyouringredients.core.domain.util.AuthResult

interface AuthSource {
    suspend fun loginWithEmail(email: String, password: String): AuthResult
    suspend fun signupWithEmail(email: String, password: String): AuthResult
    suspend fun signInWithGoogle(idToken: String): AuthResult
    fun isUserLoggedIn(): Boolean
}