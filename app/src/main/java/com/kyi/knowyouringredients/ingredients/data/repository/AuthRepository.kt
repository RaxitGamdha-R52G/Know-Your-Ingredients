package com.kyi.knowyouringredients.ingredients.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.kyi.knowyouringredients.core.domain.util.AuthResult
import com.kyi.knowyouringredients.ingredients.domain.repository.AuthSource
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) : AuthSource {

    override suspend fun signupWithEmail(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            AuthResult.Success(firebaseAuth.currentUser)
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Sign-up failed: ${e.message}", e)
            AuthResult.Error(
                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
                    is FirebaseAuthUserCollisionException -> "Email already in use"
                    else -> e.message ?: "Sign-up failed"
                }
            )
        }
    }

    override suspend fun loginWithEmail(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = firebaseAuth.currentUser
            if (user != null && user.isEmailVerified) {
                android.util.Log.d("AuthRepository", "Login successful: ${user.email}")
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Email not verified")
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Login failed: ${e.message}", e)
            AuthResult.Error(
                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                    is FirebaseAuthInvalidUserException -> "No account found with this email"
                    else -> e.message ?: "Login failed"
                }
            )
        }
    }

    override suspend fun signInWithGoogle(idToken: String): AuthResult {
        return try {
            android.util.Log.d("AuthRepository", "Attempting Google Sign-In with ID token")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            android.util.Log.d("AuthRepository", "Google Sign-In successful: ${result.user?.email}")
            AuthResult.Success(result.user)
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Google Sign-In failed: ${e.message}", e)
            AuthResult.Error("Google sign-in failed")
        }
    }

    override fun isUserLoggedIn(): Boolean {
        val user = firebaseAuth.currentUser
        val isLoggedIn = user != null && user.isEmailVerified
        android.util.Log.d("AuthRepository", "Checking login: user=${user?.email}, verified=${user?.isEmailVerified}, isLoggedIn=$isLoggedIn")
        return isLoggedIn
    }
}