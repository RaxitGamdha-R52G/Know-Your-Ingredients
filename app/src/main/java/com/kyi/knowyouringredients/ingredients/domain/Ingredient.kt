package com.kyi.knowyouringredients.ingredients.domain


data class Ingredient(
    val name: String,                       // Ingredient name
    val description: String? = null,        // Description with fallback
    val isVegan: String? = null,            // Vegan status (e.g., "yes", "no", "maybe")
    val isVegetarian: String? = null,       // Vegetarian status (e.g., "yes", "no", "maybe")
    val percentEstimate: Double? = null,    // Percentage estimate (optional)
    val percentMin: Double? = null,         // Minimum percentage (optional)
    val percentMax: Double? = null,         // Maximum percentage (optional)
)
