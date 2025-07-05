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
//        NavHost(
//            navController = navController,
//            startDestination = "product_list",
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable("product_list") {
//                val viewModel = koinViewModel<ProductListViewModel>()
//                val state by viewModel.state.collectAsStateWithLifecycle()
//                val context = LocalContext.current
//                ObserveAsEvents(events = viewModel.events) { event ->
//                    when (event) {
//                        is ProductListEvent.Error -> {
//                            Toast.makeText(
//                                context,
//                                event.error.toString(context),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//
//                        is ProductListEvent.NavigateToProductDetail -> {
////                            try {
////                                val productJson = Json.encodeToString<ProductUI>(event.productUI)
////                                navController.navigate("product_detail/$productJson")
////                            } catch (e: Exception) {
////                                Toast.makeText(
////                                    context,
////                                    "Failed to navigate to product details screen",
////                                    Toast.LENGTH_SHORT
////                                ).show()
////                            }
//                        }
//                    }
//                }
//                ProductListScreen(
//                    state = state,
//                    onAction = (viewModel::onAction)
////                    onProductClick = { viewModel.onAction(ProductListAction.OnProductClicked(it)) },
////                    onLoadMore = { viewModel.onAction(ProductListAction.LoadMore) }
//                )
//            }
//            composable(
//                route = "product_detail/{productJson}",
//                arguments = listOf(
//                    navArgument("productJson") { type = NavType.StringType }
//                )
//            ) { backStackEntry ->
//                val productJson = backStackEntry.arguments?.getString("productJson")
//                val product = productJson?.let {
//                    try {
//                        Json.decodeFromString<ProductUI>(it)
//                    } catch (e: Exception) {
//                        null
//                    }
//                }
//                product?.let {
//                    ProductDetailScreen(productUI = it)
//                } ?: run {
//                    Toast.makeText(
//                        LocalContext.current,
//                        "Failed to load product details",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
    }
}