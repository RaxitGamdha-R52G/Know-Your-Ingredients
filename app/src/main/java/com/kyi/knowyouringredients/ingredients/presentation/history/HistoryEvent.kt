package com.kyi.knowyouringredients.ingredients.presentation.history

import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

sealed interface HistoryEvent {
    data class NavigateToProductDetail(val productUI: ProductUI) : HistoryEvent
    data class ShowError(val message: String) : HistoryEvent

    object ShowUndoSnackbar : HistoryEvent
}