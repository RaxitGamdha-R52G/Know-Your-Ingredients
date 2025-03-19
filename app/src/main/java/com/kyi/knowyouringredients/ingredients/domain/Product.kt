package com.kyi.knowyouringredients.ingredients.domain

data class Product(
    val code: String,
    val productName: String,
    val brands: List<String> = emptyList(),
    val packaging: List<Packaging> = emptyList<Packaging>(),
    val ingredients: List<Ingredient> = emptyList(), //ingredients_tags
    val nutriments: Nutriments? = null,
    val nutritionGrade: String? = null,
    val nutritionScore: Int? = null,
)
