package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Ingredient

data class IngredientUI(
    val name: String,                   // Ingredient name
    val description: String,            // Description with fallback
    val isVegan: String,                // Vegan status (e.g., "yes", "no", "maybe")
    val isVegetarian: String,           // Vegetarian status (e.g., "yes", "no", "maybe")
    val percentEstimate: String         // Formatted percentage (e.g., "50%")
) {
    companion object {
        // Factory function to create IngredientUI from domain Ingredient
        fun fromDomain(ingredient: Ingredient): IngredientUI {
            return IngredientUI(
                name = ingredient.name,
                description = ingredient.description ?: "No description available",
                isVegan = ingredient.isVegan ?: "Unknown",
                isVegetarian = ingredient.isVegetarian ?: "Unknown",
                percentEstimate = ingredient.percentEstimate?.let { "$it%" } ?: "N/A"
            )
        }
    }
}