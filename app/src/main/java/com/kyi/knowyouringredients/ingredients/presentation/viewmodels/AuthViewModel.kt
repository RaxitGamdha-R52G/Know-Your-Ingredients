package com.kyi.knowyouringredients.ingredients.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kyi.knowyouringredients.core.domain.util.AuthResult
import com.kyi.knowyouringredients.ingredients.domain.repository.AuthSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {
    private val authSource: AuthSource by inject()
    private val firebaseAuth: FirebaseAuth by inject()

    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState: StateFlow<AuthResult?> = _authState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun signupWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authState.value = authSource.signupWithEmail(email, password)
            _isLoading.value = false
        }
    }

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authState.value = authSource.loginWithEmail(email, password)
            _isLoading.value = false
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _authState.value = authSource.signInWithGoogle(idToken)
            _isLoading.value = false
        }
    }

    fun checkUserLoggedIn(): Boolean {
        return authSource.isUserLoggedIn()
    }

    fun clearAuthState() {
        _authState.value = null
    }
}