package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.NutritionInfo

data class NutritionInfoUI(
    val grade: String,
    val score: String,
    val nutrientLevels: Map<String, String>,
    val novaGroup: String
) {
    companion object {
        fun fromDomain(nutritionInfo: NutritionInfo?): NutritionInfoUI {
            return NutritionInfoUI(
                grade = nutritionInfo?.grade?.uppercase() ?: "N/A",
                score = nutritionInfo?.score?.toString() ?: "N/A",
                nutrientLevels = nutritionInfo?.nutrientLevels ?: emptyMap(),
                novaGroup = nutritionInfo?.novaGroup?.toString() ?: "N/A"
            )
        }
    }
}