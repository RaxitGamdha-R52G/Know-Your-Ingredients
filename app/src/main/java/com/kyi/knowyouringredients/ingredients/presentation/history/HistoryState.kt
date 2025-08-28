package com.kyi.knowyouringredients.ingredients.presentation.history

import com.kyi.knowyouringredients.ingredients.domain.models.HistoryItem
import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

enum class HistoryFilter(val key: String) {
    SCANNED("scanned"),
    SEARCHED("searched")
}

data class HistoryState(
    val isLoading: Boolean = true,
    val historyItems: List<HistoryItem> = emptyList(),
    val selectedFilter: HistoryFilter = HistoryFilter.SCANNED,
    val error: String? = null,
    val productDetail: ProductUI? = null
)