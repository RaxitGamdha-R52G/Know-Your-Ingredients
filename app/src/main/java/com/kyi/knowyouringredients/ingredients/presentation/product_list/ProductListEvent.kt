package com.kyi.knowyouringredients.ingredients.presentation.product_list

import com.kyi.knowyouringredients.core.domain.util.NetworkError

sealed interface ProductListEvent {
    data class Error(val error: NetworkError) : ProductListEvent
//    data class NavigateToProductDetail(val productUI: ProductUI) : ProductListEvent
}