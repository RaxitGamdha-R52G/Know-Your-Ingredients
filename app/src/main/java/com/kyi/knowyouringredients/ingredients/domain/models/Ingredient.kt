package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("id") val id: String,
    @SerialName("text") val name: String,
    @SerialName("vegan") val isVegan: String? = null,
    @SerialName("vegetarian") val isVegetarian: String? = null,
    @SerialName("percent_estimate") val percentEstimate: Double? = null,
    @SerialName("ingredients") val subIngredients: List<Ingredient> = emptyList()
)