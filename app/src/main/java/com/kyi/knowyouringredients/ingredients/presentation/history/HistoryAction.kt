package com.kyi.knowyouringredients.ingredients.presentation.history

import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem

sealed interface HistoryAction {
    data class OnFilterChanged(val filter: HistoryFilter) : HistoryAction
    data class OnProductClicked(val code: String) : HistoryAction
    data class OnItemSwiped(val item: HistoryItem) : HistoryAction
}
