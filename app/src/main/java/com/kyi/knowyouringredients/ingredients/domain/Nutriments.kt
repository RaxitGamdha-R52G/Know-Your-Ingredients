package com.kyi.knowyouringredients.ingredients.domain

data class Nutriments(
    val energyKcal100g: Double? = null,             // Energy in kcal
    val fat100g: Double? = null,                    // Total fat
    val saturatedFat100g: Double? = null,           // Saturated fat
    val carbohydrates100g: Double? = null,          // Total carbohydrates
    val sugars100g: Double? = null,                 // Sugars
    val proteins100g: Double? = null,               // Proteins
    val salt100g: Double? = null,                   // Salt
    val additivesN: Int? = null,                    // Additives Count
    val additivesTags: List<String> = emptyList()   // Additives Tags
)
