package com.kyi.knowyouringredients.ingredients.presentation.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.core.domain.util.AuthResult
import com.kyi.knowyouringredients.core.navigation.AuthDestination
import com.kyi.knowyouringredients.core.navigation.MainScreenDestination
import com.kyi.knowyouringredients.core.navigation.models.NavItem
import com.kyi.knowyouringredients.core.presentation.util.ObserveAsEvents
import com.kyi.knowyouringredients.core.presentation.util.toString
import com.kyi.knowyouringredients.ingredients.presentation.components.ErrorCard
import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.ProductDetailScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListState
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreen
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenAction
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenEvent
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreen
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenEvent
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.AuthViewModel
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.ScanViewModel
import com.kyi.knowyouringredients.ingredients.presentation.viewmodels.SearchViewModel
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    topNavController: NavHostController,
    searchViewModel: SearchViewModel = koinViewModel(),
    scanViewModel: ScanViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val searchState = searchViewModel.state.collectAsStateWithLifecycle()
    val scanState = scanViewModel.state.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                Log.d(
                    "MainScreen",
                    "Navigating to product detail: ${event.productUI.productName}, code: ${event.productUI.code}"
                )
                navController.navigate("product_detail/${event.productUI.code}") {
                    popUpTo(MainScreenDestination.Scan.route) { inclusive = false }
                }
            }
        }
    }

    // State for selected navigation item
    var selectedItem by remember { mutableIntStateOf(1) } // Default to Scan

    // Define navigation items
    val navItems = listOf(
        NavItem(
            destination = MainScreenDestination.Search,
            label = stringResource(id = R.string.nav_search),
            icon = Icons.Default.Search
        ),
        NavItem(
            destination = MainScreenDestination.Scan,
            label = stringResource(id = R.string.nav_scan),
            icon = Icons.Default.CameraEnhance
        ),
        NavItem(
            destination = MainScreenDestination.Lists,
            label = stringResource(id = R.string.nav_lists),
            icon = Icons.AutoMirrored.Filled.List
        )
    )

    // Track current back stack entry to sync bottom bar
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
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
                            if (item.destination == MainScreenDestination.Scan) {
                                scanViewModel.onAction(ScanScreenAction.ResumeScanning)
                            }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NavHost(
                navController = navController,
                startDestination = MainScreenDestination.Scan.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(MainScreenDestination.Search.route) {
                    SearchScreen(
                        state = searchState.value,
                        onAction = { action -> searchViewModel.onAction(action) },
                        modifier = Modifier.fillMaxSize(),
                        innerPadding = innerPadding
                    )
                }
                composable(MainScreenDestination.Scan.route) {
                    ScanScreen(
                        state = scanState.value,
                        onAction = { action -> scanViewModel.onAction(action) },
                        onPermissionResult = { isGranted ->
                            scanViewModel.onPermissionResult(
                                isGranted
                            )
                        },
                        onInitializeCamera = { previewView, lifecycleOwner ->
                            scanViewModel.initializeCamera(previewView, lifecycleOwner)
                        },
                        onToggleFlashlight = { isEnabled -> scanViewModel.toggleFlashlight(isEnabled) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(MainScreenDestination.Lists.route) {
                    HistoryScreen()
                }
                composable("product_detail/{code}") { backStackEntry ->
                    val code = backStackEntry.arguments?.getString("code") ?: ""
                    Log.d("MainScreen", "ProductDetailScreen for code: $code")
                    val productUI = scanState.value.selectedProduct?.takeIf { it.code == code }
                        ?: searchState.value.selectedProduct?.takeIf { it.code == code }
                    val selectedState = when {
                        scanState.value.selectedProduct?.code == code -> {
                            Log.d(
                                "MainScreen",
                                "Using Scan state, product: ${scanState.value.selectedProduct?.productName}"
                            )
                            ProductListState.Scan(scanState.value)
                        }

                        searchState.value.selectedProduct?.code == code -> {
                            Log.d(
                                "MainScreen",
                                "Using Search state, product: ${searchState.value.selectedProduct?.productName}"
                            )
                            ProductListState.Search(searchState.value)
                        }

                        else -> {
                            Log.w(
                                "MainScreen",
                                "No matching product for code: $code, using default Search state"
                            )
                            ProductListState.Search(searchState.value.copy(selectedProduct = productUI))
                        }
                    }
                    ProductDetailScreen(
                        state = selectedState,
                        onBack = {
                            Log.d("MainScreen", "Back pressed, clearing selected product")
                            when (selectedState) {
                                is ProductListState.Scan -> {
                                    scanViewModel.clearSelectedProduct()
                                    scanViewModel.onAction(ScanScreenAction.ResumeScanning)
                                }

                                is ProductListState.Search -> searchViewModel.clearSelectedProduct()
                            }
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxSize(),
                        innerPadding = innerPadding
                    )
                }
            }
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    GoogleSignIn.getClient(
                        context,
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(context.getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()
                    ).signOut()
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    topNavController.navigate(AuthDestination.Login.route) {
                        popUpTo(AuthDestination.Main.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Logout")
            }
        }
    }

    if (errorMessage != null) {
        ErrorCard(
            errorMessage = errorMessage.toString(),
            onDismiss = { errorMessage = null }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    KnowYourIngredientsTheme {
        MainScreen(topNavController = rememberNavController())
    }
}