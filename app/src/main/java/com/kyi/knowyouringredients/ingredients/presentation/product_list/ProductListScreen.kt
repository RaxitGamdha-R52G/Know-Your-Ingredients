package com.kyi.knowyouringredients.ingredients.presentation.product_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.R
import com.kyi.knowyouringredients.ingredients.presentation.product_list.component.ProductListItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
fun ProductListScreen(
    state: ProductListState,
    onAction: (ProductListAction) -> Unit,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val currentState by rememberUpdatedState(state)

    // Log state for debugging
    Log.d(
        "ProductListScreen", "Received state: products=${state.products.size}, " +
                "page=${state.page}, totalCount=${state.totalCount}, isLoading=${state.isLoading}"
    )

    // Detect when the user scrolls to the end with debouncing
    LaunchedEffect(listState, currentState) {
        snapshotFlow { listState.layoutInfo }
            .distinctUntilChanged()
            .debounce(300.milliseconds)
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                Log.d(
                    "ProductListScreen",
                    "Total items: $totalItems, Last visible: $lastVisibleItem, " +
                            "isLoading: ${currentState.isLoading}, Page: ${currentState.page}, " +
                            "PageSize: ${currentState.pageSize}, TotalCount: ${currentState.totalCount}"
                )
                if (totalItems > 0 &&
                    lastVisibleItem >= totalItems - 1 &&
                    !currentState.isLoading &&
                    currentState.page * currentState.pageSize < currentState.totalCount
                ) {
                    Log.d("ProductListScreen", "Triggering LoadMore action")
                    onAction(ProductListAction.LoadMore)
                }
            }
    }

    if (state.isLoading && state.products.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.products.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_products_found),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.products, key = { it.code }) { product ->
                    ProductListItem(
                        productUI = product,
                        onClick = { onAction(ProductListAction.OnProductClicked(product)) }
                    )
                }
                if (state.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}