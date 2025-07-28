package com.kyi.knowyouringredients

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kyi.knowyouringredients.ingredients.presentation.main.MainScreen
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreen
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            KoinAndroidContext {
//                KnowYourIngredientsTheme {
//                    MainScreen()
////                    SearchScreen()
//                }
//            }

            KnowYourIngredientsTheme {
                MainScreen()
            }
        }
    }
}