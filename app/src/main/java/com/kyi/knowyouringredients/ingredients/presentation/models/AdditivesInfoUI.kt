package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.AdditivesInfo

data class AdditivesInfoUI(
    val count: String,
    val tags: List<String>
) {
    companion object {
        fun fromDomain(additivesInfo: AdditivesInfo?): AdditivesInfoUI {
            return AdditivesInfoUI(
                count = additivesInfo?.count?.toString() ?: "N/A",
                tags = additivesInfo?.tags?.map { it.replace(Regex("^[a-z]{2}:"), "") }
                    ?: emptyList()
            )
        }
    }
}