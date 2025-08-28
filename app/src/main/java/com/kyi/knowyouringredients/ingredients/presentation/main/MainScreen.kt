package com.kyi.knowyouringredients.ingredients.presentation.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.core.domain.util.AuthResult
import com.kyi.knowyouringredients.core.navigation.AuthDestination
import com.kyi.knowyouringredients.core.navigation.MainScreenDestination
import com.kyi.knowyouringredients.core.navigation.models.NavItem
import com.kyi.knowyouringredients.core.presentation.util.ObserveAsEvents
import com.kyi.knowyouringredients.core.presentation.util.toString
import com.kyi.knowyouringredients.ingredients.presentation.components.ErrorCard
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryEvent
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.ProductDetailScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreen
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenAction
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenEvent
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreen
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenEvent
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenState
import com.kyi.knowyouringredients.ingredients.presentation.settings.SettingsScreen
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.AuthViewModel
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.HistoryViewModel
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.ScanViewModel
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.SearchViewModel
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    topNavController: NavHostController,
    searchViewModel: SearchViewModel = koinViewModel(),
    scanViewModel: ScanViewModel = koinViewModel(),
    historyViewModel: HistoryViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    KnowYourIngredientsTheme {
        val context = LocalContext.current
        val searchState = searchViewModel.state.collectAsStateWithLifecycle()
        val scanState = scanViewModel.state.collectAsStateWithLifecycle()
        val historyState by historyViewModel.state.collectAsStateWithLifecycle()
        val authState by authViewModel.authState.collectAsStateWithLifecycle()
        var errorMessage by remember { mutableStateOf<String?>(null) }


        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        // Handle session invalidation
        LaunchedEffect(authState) {
            when (val result = authState) {
                is AuthResult.Error -> {
                    if (result.message == "Session expired or account invalid. Please log in again.") {
                        Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                        authViewModel.clearAuthState()
                        topNavController.navigate(AuthDestination.Login.route) {
                            popUpTo(AuthDestination.Main.route) { inclusive = true }
                        }
                    }
                }
                else -> Unit
            }
        }

        // Observe SearchViewModel events
        ObserveAsEvents(events = searchViewModel.events, key1 = searchViewModel) { event ->
            Log.d("MainScreen", "Received SearchScreenEvent: $event")
            when (event) {
                is SearchScreenEvent.Error -> {
                    val message = event.error.toString(context)
                    if (message.isNotBlank()) {
                        Log.d("MainScreen", "Showing error: $message")
                        errorMessage = message
                    }
                }
                is SearchScreenEvent.NavigateToProductDetail -> {
                    Log.d("MainScreen", "Navigating to product detail: ${event.productUI.productName}")
                    navController.navigate("product_detail/${event.productUI.code}") {
                        popUpTo(MainScreenDestination.Search.route) { inclusive = false }
                    }
                }
            }
        }

        // Observe ScanViewModel events
        ObserveAsEvents(events = scanViewModel.events, key1 = scanViewModel) { event ->
            Log.d("MainScreen", "Received ScanScreenEvent: $event")
            when (event) {
                is ScanScreenEvent.Error -> {
                    val message = event.error.toString(context)
                    if (message.isNotBlank()) {
                        Log.d("MainScreen", "Showing error: $message")
                        errorMessage = message
                    }
                }
                is ScanScreenEvent.NavigateToProductDetail -> {
                    Log.d("MainScreen", "Navigating to product detail: ${event.productUI.productName}, code: ${event.productUI.code}")
                    navController.navigate("product_detail/${event.productUI.code}") {
                        popUpTo(MainScreenDestination.Scan.route) { inclusive = false }
                    }
                }
            }
        }

        // --- OBSERVE HistoryViewModel events
        ObserveAsEvents(events = historyViewModel.events) { event ->
            when (event) {
                is HistoryEvent.NavigateToProductDetail -> {
                    navController.navigate("product_detail/${event.productUI.code}")
                }
                is HistoryEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is HistoryEvent.ShowUndoSnackbar -> {
                    coroutineScope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Item removed",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            historyViewModel.onUndoDelete()
                        } else {
                            historyViewModel.onConfirmDelete()
                        }
                    }
                }
            }
        }

        var selectedItem by remember { mutableIntStateOf(1) } // Default to Scan

        val navItems = listOf(
            NavItem(destination = MainScreenDestination.Search, label = stringResource(id = R.string.nav_search), icon = Icons.Default.Search),
            NavItem(destination = MainScreenDestination.Scan, label = stringResource(id = R.string.nav_scan), icon = Icons.Default.CameraEnhance),
            NavItem(destination = MainScreenDestination.Lists, label = stringResource(id = R.string.nav_lists), icon = Icons.AutoMirrored.Filled.List),
            NavItem(destination = MainScreenDestination.Settings, label = stringResource(id = R.string.nav_settings), icon = Icons.Default.Settings)
        )

        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        selectedItem = navItems.indexOfFirst { it.destination.route == currentRoute }.takeIf { it >= 0 } ?: selectedItem


        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.Cyan,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    navItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                if (item.destination == MainScreenDestination.Scan) {
                                    scanViewModel.onAction(ScanScreenAction.ResumeScanning)
                                }
                                navController.navigate(item.destination.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
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
                            label = { Text(item.label, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            },
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = MainScreenDestination.Scan.route,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                composable(MainScreenDestination.Search.route) {
                    SearchScreen(
                        state = searchState.value,
                        onAction = { action -> searchViewModel.onAction(action) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                composable(MainScreenDestination.Scan.route) {
                    ScanScreen(
                        state = scanState.value,
                        onAction = { action -> scanViewModel.onAction(action) },
                        onPermissionResult = { isGranted -> scanViewModel.onPermissionResult(isGranted) },
                        onInitializeCamera = { previewView, lifecycleOwner -> scanViewModel.initializeCamera(previewView, lifecycleOwner) },
                        onToggleFlashlight = { isEnabled -> scanViewModel.toggleFlashlight(isEnabled) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(MainScreenDestination.Lists.route) {
                    HistoryScreen(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = historyViewModel,
                        onNavigateToDetail = { productCode ->
                            navController.navigate("product_detail/$productCode")
                        }
                    )
                }
                composable(MainScreenDestination.Settings.route) {
                    SettingsScreen(
                        modifier = Modifier.fillMaxSize(),
                        navController = topNavController,
                        authViewModel = authViewModel
                    )
                }
                composable("product_detail/{code}") { backStackEntry ->
                    val code = backStackEntry.arguments?.getString("code") ?: ""
                    Log.d("MainScreen", "ProductDetailScreen for code: $code")

                    // This logic now correctly finds the right state to pass to the detail screen
                    val finalState = when {
                        scanState.value.selectedProduct?.code == code -> ProductListState.Scan(scanState.value)
                        searchState.value.selectedProduct?.code == code -> ProductListState.Search(searchState.value)
                        historyState.productDetail?.code == code -> ProductListState.History(historyState, historyState.productDetail)
                        else -> ProductListState.Search(SearchScreenState(isLoading = true)) // A fallback loading state
                    }

                    ProductDetailScreen(
                        state = finalState,
                        onBack = {
                            Log.d("MainScreen", "Back pressed, clearing selected product")
                            when (finalState) {
                                is ProductListState.Scan -> {
                                    scanViewModel.clearSelectedProduct()
                                    scanViewModel.onAction(ScanScreenAction.ResumeScanning)
                                }
                                is ProductListState.Search -> searchViewModel.clearSelectedProduct()
                                is ProductListState.History -> historyViewModel.clearProductDetail()
                            }
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            if (errorMessage != null) {
                ErrorCard(
                    errorMessage = errorMessage.toString(),
                    onDismiss = { errorMessage = null },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}