package com.kyi.knowyouringredients.ingredients.domain


data class Ingredient(
    val name: String,
    val description: String? = null,
    val isVegan: String? = null,
    val isVegetarian: String? = null,
    val percentEstimate: Double? = null,
    val percentMin: Double? = null,
    val percentMax: Double? = null,
)
