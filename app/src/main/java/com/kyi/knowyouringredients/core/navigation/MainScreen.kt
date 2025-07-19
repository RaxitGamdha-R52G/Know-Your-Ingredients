//package com.kyi.knowyouringredients.core.navigation
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.List
//import androidx.compose.material.icons.filled.CameraEnhance
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.kyi.knowyouringredients.R
//import com.kyi.knowyouringredients.core.navigation.MainScreenDestination.Lists
//import com.kyi.knowyouringredients.core.navigation.MainScreenDestination.Scan
//import com.kyi.knowyouringredients.core.navigation.MainScreenDestination.Search
//import com.kyi.knowyouringredients.core.navigation.models.NavItem
//import com.kyi.knowyouringredients.core.presentation.util.ObserveAsEvents
//import com.kyi.knowyouringredients.core.presentation.util.toString
//import com.kyi.knowyouringredients.ingredients.presentation.product_detail.ProductDetailScreen
//import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
//import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListEvent
//import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListScreen
//import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreen
//import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreen
//import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
//import com.kyi.knowyouringredients.viewmodels.ProductListViewModel
//import org.koin.androidx.compose.koinViewModel
//
//@Composable
//fun MainScreen(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController(),
//    viewModel: ProductListViewModel = koinViewModel(),
//) {
//    val context = LocalContext.current
//
//    ObserveAsEvents(events = viewModel.events) { event ->
//        when (event) {
//            is ProductListEvent.Error -> {
////                Toast.makeText(
////                    context,
////                    event.error.toString(context),
////                    Toast.LENGTH_LONG
////                )
////                    .show()
//                val message = try {
//                    event.error.toString(context).also {
//                        Log.d("MainScreen", "Error message for toast: $it")
//                    }
//                } catch (e: Exception) {
//                    Log.e("MainScreen", "Failed to get error message", e)
//                    "An error occurred"
//                }
//                if (message.isNotBlank()) {
//                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//                } else {
//                    Log.w("MainScreen", "Empty error message, skipping toast")
//                }
//            }
//        }
//    }
//
//
//    // State for selected navigation item
//    var selectedItem by remember { mutableIntStateOf(2) } // Default to Lists
//
//    // Define navigation items
//    val navItems = listOf(
//        NavItem(
//            destination = Search,
//            label = stringResource(id = R.string.nav_search),
//            icon = Icons.Default.Search
//        ),
//        NavItem(
//            destination = Scan,
//            label = stringResource(id = R.string.nav_scan),
//            icon = Icons.Default.CameraEnhance // Reusing Search icon as placeholder
//        ),
//        NavItem(
//            destination = Lists,
//            label = stringResource(id = R.string.nav_lists),
//            icon = Icons.AutoMirrored.Filled.List
//        )
//    )
//
//    // Track current back stack entry to sync bottom bar
//    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = currentBackStackEntry?.destination?.route
//
//    // Update selectedItem based on current route
//    selectedItem = navItems.indexOfFirst { it.destination.route == currentRoute }
//        .takeIf { it >= 0 } ?: selectedItem
//
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                navItems.forEachIndexed { index, item ->
//                    NavigationBarItem(
//                        selected = selectedItem == index,
//                        onClick = {
//                            selectedItem = index
//                            Log.d("MainScreen", "Selected nav item: ${item.label}")
//                            navController.navigate(item.destination.route) {
//                                // Pop up to the start destination to avoid stacking
//                                popUpTo(navController.graph.startDestinationId) {
//                                    saveState = true
//                                }
//                                // Avoid multiple instances of the same destination
//                                launchSingleTop = true
//                                // Restore state when reselecting
//                                restoreState = true
//                            }
//                        },
//                        icon = {
//                            Icon(
//                                imageVector = item.icon,
//                                contentDescription = item.label,
//                                tint = if (selectedItem == index) MaterialTheme.colorScheme.primary
//                                else MaterialTheme.colorScheme.onBackground,
//                                modifier = Modifier.size(24.dp)
//                            )
//                        },
//                        label = { Text(item.label) }
//                    )
//                }
//            }
//        },
//        modifier = modifier.fillMaxSize()
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Lists.route,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(Search.route) {
//                SearchScreen()
//            }
//            composable(Scan.route) {
//                ScanScreen(
//                    onAction = { action ->
//                        viewModel.onAction(action)
//                        when (action) {
//                            is ProductListAction.OnProductClicked -> {
//                                Log.d(
//                                    "MainScreen",
//                                    "Product selected: ${action.productUI.productName}, code: ${action.productUI.code}"
//                                )
//                                navController.navigate("product_detail/${action.productUI.code}") {
//                                    popUpTo(Scan.route) {
//                                        inclusive = false
//                                    }
//                                }
//                            }
//
//                            else -> {}
//                        }
//                    }
//                )
//            }
//            composable(Lists.route) {
//                ProductListScreen(
//                    state = viewModel.state.collectAsStateWithLifecycle().value,
//                    onAction = { action ->
//                        viewModel.onAction(action)
//                        when (action) {
//                            is ProductListAction.OnProductClicked -> {
//                                Log.d(
//                                    "MainScreen",
//                                    "Product clicked: ${action.productUI.productName}"
//                                )
//                                navController.navigate("product_detail/${action.productUI.code}")
//                            }
//
//                            else -> {}
//                        }
//                    }
//                )
//            }
//            composable("product_detail/{code}") { backStackEntry ->
//                val code = backStackEntry.arguments?.getString("code") ?: ""
//                ProductDetailScreen(
//                    state = viewModel.state.collectAsStateWithLifecycle().value,
//                    onBack = {
//                        viewModel.clearSelectedProduct()
//                        navController.popBackStack()
//                    }
//                )
//            }
//        }
//    }
//}
//
//
//// Preview with theme
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    KnowYourIngredientsTheme {
//        MainScreen()
//    }
//}

package com.kyi.knowyouringredients.core.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.core.navigation.MainScreenDestination.Lists
import com.kyi.knowyouringredients.core.navigation.MainScreenDestination.Scan
import com.kyi.knowyouringredients.core.navigation.MainScreenDestination.Search
import com.kyi.knowyouringredients.core.navigation.models.NavItem
import com.kyi.knowyouringredients.core.presentation.util.ObserveAsEvents
import com.kyi.knowyouringredients.core.presentation.util.toString
import com.kyi.knowyouringredients.ingredients.presentation.components.ErrorCard
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.ProductDetailScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListEvent
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListScreen
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreen
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreen
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import com.kyi.knowyouringredients.viewmodels.ProductListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ProductListViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle();
    var errorMessage by remember { mutableStateOf<String?>(null) }

    ObserveAsEvents(events = viewModel.events, key1 = viewModel) { event ->
        Log.d("MainScreen", "Received event: $event")
        when (event) {
            is ProductListEvent.Error -> {
                val message = try {
                    event.error.toString(context).also {
                        Log.d("MainScreen", "Error message for toast: $it")
                    }
                } catch (e: Exception) {
                    Log.e("MainScreen", "Failed to get error message", e)
                    "An error occurred"
                }
                if (message.isNotBlank()) {
//                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    Log.d("MainScreen", "Showing toast with message: $message")
                    errorMessage = message
                } else {
                    Log.w("MainScreen", "Empty error message, skipping toast")
                }
            }
        }
    }

    // State for selected navigation item
    var selectedItem by remember { mutableIntStateOf(2) } // Default to Lists

    // Define navigation items
    val navItems = listOf(
        NavItem(
            destination = Search,
            label = stringResource(id = R.string.nav_search),
            icon = Icons.Default.Search
        ),
        NavItem(
            destination = Scan,
            label = stringResource(id = R.string.nav_scan),
            icon = Icons.Default.CameraEnhance
        ),
        NavItem(
            destination = Lists,
            label = stringResource(id = R.string.nav_lists),
            icon = Icons.AutoMirrored.Filled.List
        )
    )

    // Track current back stack entry to sync bottom bar
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Update selectedItem based on current route
    selectedItem = navItems.indexOfFirst { it.destination.route == currentRoute }
        .takeIf { it >= 0 } ?: selectedItem

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            Log.d("MainScreen", "Selected nav item: ${item.label}")
                            navController.navigate(item.destination.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selectedItem == index) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Lists.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Search.route) {
                SearchScreen()
            }
            composable(Scan.route) {
                ScanScreen(
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is ProductListAction.OnProductClicked -> {
                                Log.d(
                                    "MainScreen",
                                    "Product selected: ${action.productUI.productName}, code: ${action.productUI.code}"
                                )
//                                    delay(100) // Allow time for error events
                                navController.navigate("product_detail/${action.productUI.code}") {
                                    popUpTo(Scan.route) { inclusive = false }
                                }

                            }

                            else -> {}
                        }
                    },
                    state = state.value,
                    onPermissionResult = { isGranted -> viewModel.onPermissionResult(isGranted) },
                    onInitializeCamera = { previewView -> viewModel.initializeCamera(previewView) },
                    onToggleFlashlight = { isEnabled -> viewModel.toggleFlashlight(isEnabled) },
                )
            }
            composable(Lists.route) {
                ProductListScreen(
                    state = viewModel.state.collectAsStateWithLifecycle().value,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is ProductListAction.OnProductClicked -> {
                                Log.d(
                                    "MainScreen",
                                    "Product clicked: ${action.productUI.productName}"
                                )
                                navController.navigate("product_detail/${action.productUI.code}")
                            }

                            else -> {}
                        }
                    }
                )
            }
            composable("product_detail/{code}") { backStackEntry ->
                val code = backStackEntry.arguments?.getString("code") ?: ""
                ProductDetailScreen(
                    state = viewModel.state.collectAsStateWithLifecycle().value,
                    onBack = {
                        viewModel.clearSelectedProduct()
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    if(errorMessage != null){
        ErrorCard(
            errorMessage = errorMessage.toString(),
            onDismiss = {errorMessage = null}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    KnowYourIngredientsTheme {
        MainScreen()
    }
}