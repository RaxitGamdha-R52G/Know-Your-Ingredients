package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.compose.foundation.layout.Box
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
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenAction
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
fun ProductListScreen(
    state: ProductListState,
    onAction: (ProductListAction) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    val listState = rememberLazyListState()
    val currentState by rememberUpdatedState(state)

    LaunchedEffect(listState, currentState) {
        if (currentState is ProductListState.Search) {
            snapshotFlow { listState.layoutInfo }
                .distinctUntilChanged()
                .debounce(300.milliseconds)
                .collect { layoutInfo ->
                    val totalItems = layoutInfo.totalItemsCount
                    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                    val searchState = (currentState as ProductListState.Search).state
                    if (totalItems > 0 &&
                        lastVisibleItem >= totalItems - 1 &&
                        !searchState.isLoading &&
                        searchState.page * searchState.pageSize < searchState.totalCount
                    ) {
                        onAction(ProductListAction.Search(SearchScreenAction.LoadMore))
                    }
                }
        }
    }

    when {
        state.isLoading && state.products.isEmpty() -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.products.isEmpty() -> {
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
        }
        else -> {
            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(state.products, key = { it.code }) { product ->
                    ProductListItem(
                        productUI = product,
                        onClick = {
                            onAction(
                                when (state) {
                                    is ProductListState.Search -> ProductListAction.Search(SearchScreenAction.OnProductClicked(product))
                                    is ProductListState.Scan -> ProductListAction.Scan(
                                        ScanScreenAction.OnProductClicked(product))
                                }
                            )
                        }
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