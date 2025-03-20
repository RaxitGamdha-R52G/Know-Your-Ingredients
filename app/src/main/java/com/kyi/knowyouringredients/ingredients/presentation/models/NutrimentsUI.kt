package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Nutriments

data class NutrimentsUI(
    val energyKcal: String,         // Formatted energy (e.g., "539 kcal")
    val fat: String,                // Formatted fat (e.g., "30.9 g")
    val saturatedFat: String,       // Formatted saturated fat (e.g., "10.6 g")
    val carbohydrates: String,      // Formatted carbs (e.g., "57.5 g")
    val sugars: String,             // Formatted sugars (e.g., "56.3 g")
    val proteins: String,           // Formatted proteins (e.g., "6.3 g")
    val salt: String,               // Formatted salt (e.g., "0.107 g")
    val additivesCount: Int,        // Number of additives
    val additivesTags: List<String> // List of additive tags
) {
    companion object {
        // Factory function to create NutrimentsUI from domain Nutriments
        fun fromDomain(nutriments: Nutriments?): NutrimentsUI {
            return NutrimentsUI(
                energyKcal = nutriments?.energyKcal100g?.let { "$it kcal" } ?: "N/A",
                fat = nutriments?.fat100g?.let { "$it g" } ?: "N/A",
                saturatedFat = nutriments?.saturatedFat100g?.let { "$it g" } ?: "N/A",
                carbohydrates = nutriments?.carbohydrates100g?.let { "$it g" } ?: "N/A",
                sugars = nutriments?.sugars100g?.let { "$it g" } ?: "N/A",
                proteins = nutriments?.proteins100g?.let { "$it g" } ?: "N/A",
                salt = nutriments?.salt100g?.let { "$it g" } ?: "N/A",
                additivesCount = nutriments?.additivesN ?: 0,
                additivesTags = nutriments?.additivesTags ?: emptyList()
            )
        }
    }
}