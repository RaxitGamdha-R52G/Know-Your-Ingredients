package com.kyi.knowyouringredients.ingredients.presentation.search

import com.kyi.knowyouringredients.ingredients.presentation.models.ProductUI

sealed interface SearchScreenAction {
    data class Search(
        val searchQuery: String = "",
    ) : SearchScreenAction

    data object LoadMore : SearchScreenAction
    data class OnProductClicked(val productUI: ProductUI) : SearchScreenAction
}