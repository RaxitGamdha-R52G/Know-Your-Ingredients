package com.kyi.knowyouringredients.ingredients.presentation.search

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

sealed interface SearchScreenEvent {
    data class Error(val error: NetworkError) : SearchScreenEvent
    data class NavigateToProductDetail(val productUI: ProductUI) : SearchScreenEvent
}