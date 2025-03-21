package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.CategoriesInfo

data class CategoriesInfoUI(
    val tags: List<String>
) {
    companion object {
        fun fromDomain(categoriesInfo: CategoriesInfo?): CategoriesInfoUI {
            return CategoriesInfoUI(
                tags = categoriesInfo?.tags?.map { it.replace(Regex("^[a-z]{2}:"), "") }
                    ?: emptyList()
            )
        }
    }
}