package com.kyi.knowyouringredients.ingredients.domain

data class Packaging(
    val material: String,               // Material type (e.g., "Glass", "Plastic")
    val quantityValue: Double = 1.0,    // Quantity value
    val quantityUnit: String = "g",     // Quantity unit (default: "g")
    val shape: String? = null,          // Shape (optional)
)
