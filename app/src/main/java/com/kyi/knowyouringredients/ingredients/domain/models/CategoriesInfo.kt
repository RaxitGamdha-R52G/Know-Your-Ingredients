package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesInfo(
    @SerialName("categories_tags") val tags: List<String> = emptyList()
)