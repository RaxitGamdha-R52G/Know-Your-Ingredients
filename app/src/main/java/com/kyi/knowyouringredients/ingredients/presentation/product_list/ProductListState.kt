package com.kyi.knowyouringredients.ingredients.presentation.product_list

import com.kyi.knowyouringredients.ingredients.presentation.history.HistoryState
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI
import com.kyi.knowyouringredients.ingredients.presentation.scan.ScanScreenState
import com.kyi.knowyouringredients.ingredients.presentation.search.SearchScreenState


sealed interface ProductListState {
    val isLoading: Boolean
    val products: List<ProductUI>

    data class Search(val state: SearchScreenState) : ProductListState {
        override val isLoading: Boolean = state.isLoading
        override val products: List<ProductUI> = state.products
        val totalCount: Int = state.totalCount
        val page: Int = state.page
        val pageSize: Int = state.pageSize
    }

    data class Scan(val state: ScanScreenState) : ProductListState {
        override val isLoading: Boolean = state.isLoading
        override val products: List<ProductUI> = state.selectedProduct?.let { listOf(it) } ?: emptyList()
    }

    data class History(val state: HistoryState, val product: ProductUI?) : ProductListState {
        override val isLoading: Boolean = state.isLoading
        override val products: List<ProductUI> = product?.let { listOf(it) } ?: emptyList()
    }
}