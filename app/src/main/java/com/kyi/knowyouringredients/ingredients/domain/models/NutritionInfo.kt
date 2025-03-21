package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NutritionInfo(
    @SerialName("nutrition_grade_fr") val grade: String? = null,
    @SerialName("nutrition_score_fr") val score: Int? = null,
    @SerialName("nutrient_levels") val nutrientLevels: Map<String, String>? = null,
    @SerialName("nova_group") val novaGroup: Int? = null
)