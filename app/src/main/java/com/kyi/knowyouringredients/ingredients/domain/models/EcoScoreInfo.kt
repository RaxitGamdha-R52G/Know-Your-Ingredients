package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EcoScoreInfo(
    @SerialName("ecoscore_grade") val grade: String? = null,
    @SerialName("ecoscore_score") val score: Int? = null
)