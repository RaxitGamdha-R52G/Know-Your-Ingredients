package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.productPreview
import com.kyi.knowyouringredients.ingredients.presentation.product_list.component.ProductListItem
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme

@Composable
fun ProductListScreen(
    state: ProductListState,
    onProductClick: (ProductUI) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading && state.products.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.products.isEmpty() -> {
                Text(
                    text = "No products found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.products) { product ->
                        ProductListItem(
                            productUI = product,
                            onClick = { onProductClick(product) }
                        )
                    }
                    item {
                        if (state.page * state.pageSize < state.totalCount) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = onLoadMore,
                                    enabled = !state.isLoading
                                ) {
                                    Text("Load More")
                                    if (state.isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
private fun ProductListScreenPreview() {
    KnowYourIngredientsTheme {
        ProductListScreen(
            state = ProductListState(
                isLoading = false,
                products = (1..10).map {
                    ProductUI.fromDomain(
                        productPreview.copy(
                            productName = "Product $it",
                            nutritionInfo = productPreview.nutritionInfo?.copy(
                                grade = listOf("A", "B", "C", "D", "E").random()
                            ),
                            ecoScoreInfo = productPreview.ecoScoreInfo?.copy(
                                grade = listOf("A", "B", "C").random(),
                                score = (50..90).random()
                            ),
                            imageInfo = productPreview.imageInfo?.copy(
                                frontThumbUrl = "https://example.com/thumb$it.jpg"
                            )
                        )
                    )
                },
                totalCount = 25
            ),
            onProductClick = { /* No-op */ },
            onLoadMore = { /* TODO */ }
        )
    }
}