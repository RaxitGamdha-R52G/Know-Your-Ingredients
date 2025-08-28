package com.kyi.knowyouringredients.ingredients.presentation.search

import androidx.compose.runtime.Immutable
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

@Immutable
data class SearchScreenState(
    val isLoading: Boolean = false,
    val products: List<ProductUI> = emptyList(),
    val selectedProduct: ProductUI? = null,
    val page: Int = 1,
    val pageSize: Int = 24,
    val totalCount: Int = 0,
    val searchQuery: String = "",
    val searchPerformed: Boolean = false
)