package com.kyi.knowyouringredients.ingredients.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("id") val id: String,
    @SerialName("text") val name: String,
    @SerialName("vegan") val isVegan: String? = null,
    @SerialName("vegetarian") val isVegetarian: String? = null,
    @SerialName("percent") val percent: Double? = null, // Exact percentage when available
    @SerialName("percent_estimate") val percentEstimate: Double? = null,
    @SerialName("percent_min") val percentMin: Double? = null,
    @SerialName("percent_max") val percentMax: Double? = null,
    @SerialName("ciqual_food_code") val ciqualFoodCode: String? = null,
    @SerialName("ecobalyse_code") val ecobalyseCode: String? = null,
    @SerialName("from_palm_oil") val fromPalmOil: String? = null,
    @SerialName("is_in_taxonomy") val isInTaxonomy: Int? = null,
    @SerialName("ingredients") val subIngredients: List<Ingredient> = emptyList()
)