package com.kyi.knowyouringredients.ingredients.presentation.product_list

import androidx.compose.runtime.Immutable
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

@Immutable
data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<ProductUI> = emptyList(),
    val selectedProduct: ProductUI? = null,
)
