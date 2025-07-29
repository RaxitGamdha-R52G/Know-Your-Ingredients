package com.kyi.knowyouringredients.ingredients.presentation.signup

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kyi.knowyouringredients.core.domain.util.AuthResult
import com.kyi.knowyouringredients.core.navigation.AuthDestination
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel()
) {
    val authState by viewModel.authState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authState) {
        when (val result = authState) {
            is AuthResult.Success -> {
                Toast.makeText(
                    context,
                    "Verification email sent! Please check your inbox.",
                    Toast.LENGTH_LONG
                ).show()
//                kotlinx.coroutines.delay(2000)
                viewModel.clearAuthState()
                navController.navigate(AuthDestination.Login.route) {
                    popUpTo(AuthDestination.SignUp.route) { inclusive = true }
                }
            }
            is AuthResult.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                viewModel.clearAuthState()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = validateEmail(it)
            },
            label = { Text("Email") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = validatePassword(it)
                confirmPasswordError = validateConfirmPassword(password, confirmPassword)
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = validateConfirmPassword(password, it)
            },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPasswordError != null,
            supportingText = { confirmPasswordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                emailError = validateEmail(email)
                passwordError = validatePassword(password)
                confirmPasswordError = validateConfirmPassword(password, confirmPassword)
                if (emailError == null && passwordError == null && confirmPasswordError == null) {
                    viewModel.signupWithEmail(email, password)
                } else {
                    Toast.makeText(context, "Please fix input errors", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Sign Up")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Already have an account? Login")
        }
    }
}

private fun validateEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email cannot be empty"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
        else -> null
    }
}

private fun validatePassword(password: String): String? {
    return when {
        password.isBlank() -> "Password cannot be empty"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }
}

private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
    return when {
        confirmPassword.isBlank() -> "Confirm password cannot be empty"
        confirmPassword != password -> "Passwords do not match"
        else -> null
    }
}