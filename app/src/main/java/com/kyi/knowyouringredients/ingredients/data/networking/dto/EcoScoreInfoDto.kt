package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EcoScoreInfoDto(
    @SerialName("ecoscore_grade") val grade: String? = null,
    @SerialName("ecoscore_score") val score: Int? = null
)