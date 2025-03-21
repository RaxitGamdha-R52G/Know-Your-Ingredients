package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.AllergensInfo

data class AllergensInfoUI(
    val tags: List<String>
) {
    companion object {
        fun fromDomain(allergensInfo: AllergensInfo?): AllergensInfoUI {
            return AllergensInfoUI(
                tags = allergensInfo?.tags?.map { it.replace(Regex("^[a-z]{2}:"), "") }
                    ?: emptyList()
            )
        }
    }
}