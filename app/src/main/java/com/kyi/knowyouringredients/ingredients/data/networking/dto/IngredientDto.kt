package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    @SerialName("id") val id: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("vegan") val vegan: String? = null,
    @SerialName("vegetarian") val vegetarian: String? = null,
    @SerialName("percent_estimate") val percentEstimate: Double? = null,
    @SerialName("ingredients") val subIngredients: List<IngredientDto>? = null
)