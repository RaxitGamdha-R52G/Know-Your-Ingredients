package com.kyi.knowyouringredients.ingredients.presentation.scan

import com.kyi.knowyouringredients.core.domain.util.NetworkError
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

sealed interface ScanScreenEvent {
    data class Error(val error: NetworkError) : ScanScreenEvent
    data class NavigateToProductDetail(val productUI: ProductUI) : ScanScreenEvent
}