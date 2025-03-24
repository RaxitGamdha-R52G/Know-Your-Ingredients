package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.EcoScoreInfo

data class EcoScoreInfoUI(
    val grade: String,
    val score: String
) {
    companion object {
        fun fromDomain(ecoScoreInfo: EcoScoreInfo?): EcoScoreInfoUI {
            return EcoScoreInfoUI(
                grade = ecoScoreInfo?.grade?.uppercase() ?: "N/A",
                score = ecoScoreInfo?.score?.toString() ?: "N/A"
            )
        }
    }
}