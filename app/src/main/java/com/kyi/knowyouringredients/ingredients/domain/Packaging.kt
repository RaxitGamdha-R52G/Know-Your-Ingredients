package com.kyi.knowyouringredients.ingredients.domain

data class Packaging(
    val material: String,
    val quantityValue: Double = 1.0,
    val quantityUnit: String = "g",
    val shape: String? = null,
)
