package com.kyi.knowyouringredients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyi.knowyouringredients.core.navigation.AuthDestination
import com.kyi.knowyouringredients.ingredients.presentation.login.LoginScreen
import com.kyi.knowyouringredients.ingredients.presentation.main.MainScreen
import com.kyi.knowyouringredients.ingredients.presentation.signup.SignUpScreen
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.AuthViewModel
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KnowYourIngredientsTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    val isUserLoggedIn by remember { mutableStateOf(authViewModel.checkUserLoggedIn()) }

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            navController.navigate(AuthDestination.Main.route) {
                popUpTo(AuthDestination.Login.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AuthDestination.Login.route,
        route = AuthDestination.AUTH_NAVIGATION_GRAPH
    ) {
        composable(AuthDestination.Login.route) {
            LoginScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        composable(AuthDestination.SignUp.route) {
            SignUpScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        composable(AuthDestination.Main.route) {
            MainScreen(
                topNavController = navController,
                authViewModel = authViewModel
            )
        }
    }
}