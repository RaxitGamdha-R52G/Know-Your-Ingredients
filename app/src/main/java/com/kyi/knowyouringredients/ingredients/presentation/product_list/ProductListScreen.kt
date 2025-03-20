package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.product_list.component.ProductListItem
import com.kyi.knowyouringredients.ingredients.presentation.product_list.component.productPreview
import com.kyi.knowyouringredients.ui.theme.KnowYourIngredientsTheme
import kotlin.random.Random

@Composable
fun ProductListScreen(
    state: ProductListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.products) { productUi ->
                ProductListItem(
                    productUI = productUi,
                    onClick = {/* TODO */ }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductListScreenPreview() {
    KnowYourIngredientsTheme {
        ProductListScreen(
            state = ProductListState(
                isLoading = false,
                products = (1..100).map {
                    ProductUI.fromDomain(
                        productPreview.copy(
                            productName = "Product $it",
                            nutritionGrade = listOf<String>("A", "B", "C", "D", "E")
                                [
                                Random.nextInt(0, 5)
                            ]
                        )
                    )
                }
            )
        )
    }
}