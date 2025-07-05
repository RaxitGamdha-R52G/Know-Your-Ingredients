package com.kyi.knowyouringredients.core.navigation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyi.knowyouringredients.core.presentation.util.ObserveAsEvents
import com.kyi.knowyouringredients.core.presentation.util.toString
import com.kyi.knowyouringredients.ingredients.presentation.product_detail.ProductDetailScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListAction
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListEvent
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListScreen
import com.kyi.knowyouringredients.ingredients.presentation.product_list.ProductListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveProductListDetailPane(
    modifier: Modifier,
    viewModel: ProductListViewModel = koinViewModel()
) {
    // Log view model instance to check for multiple instances
    Log.d("AdaptiveProductListDetailPane", "ViewModel instance: $viewModel")

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()

    // Log state for debugging
    Log.d(
        "AdaptiveProductListDetailPane", "State: products=${state.products.size}, " +
                "page=${state.page}, totalCount=${state.totalCount}, isLoading=${state.isLoading}"
    )

    // Handle system back button
    BackHandler(
        enabled = navigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Detail,
        onBack = {
            viewModel.clearSelectedProduct()
            coroutineScope.launch {
                navigator.navigateTo(ListDetailPaneScaffoldRole.List)
            }
        }
    )

    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is ProductListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            ProductListScreen(
                state = state,
                onAction = { action ->
                    viewModel.onAction(action)
                    when (action) {
                        is ProductListAction.OnProductClicked -> {
                            coroutineScope.launch {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                        }

                        is ProductListAction.LoadMore -> {
                            // Handled by ProductListScreen
                        }

                        is ProductListAction.Search -> TODO()
                    }
                }
            )
        },
        detailPane = {
            ProductDetailScreen(
                state = state,
            )
        },
        modifier = modifier,
    )
}