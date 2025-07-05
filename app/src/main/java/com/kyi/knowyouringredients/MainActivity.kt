package com.kyi.knowyouringredients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kyi.knowyouringredients.core.navigation.AdaptiveProductListDetailPane
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                KnowYourIngredientsTheme {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AdaptiveProductListDetailPane(modifier = Modifier.padding(innerPadding))
    }
}