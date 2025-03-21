package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Product

data class ProductUI(
    val code: String,
    val productName: String,
    val brands: List<String> = emptyList(),
    val packaging: List<PackagingUI> = emptyList(),
    val ingredients: List<IngredientUI> = emptyList(),
    val nutriments: NutrimentsUI? = null,
    val nutritionGrade: String? = null,
    val nutritionScore: Int? = null,
    val additivesCount: Int? = null,
    val additivesTags: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val quantity: String? = null,
    val servingSize: String? = null
) {
    companion object {
        fun fromDomain(product: Product): ProductUI {
            return ProductUI(
                code = product.code,
                productName = product.productName.ifEmpty { "Unknown Product" },
                brands = product.brands?.split(",")?.map { it.trim() } ?: emptyList(),
                packaging = product.packaging.map { PackagingUI.fromDomain(it) },
                ingredients = product.ingredients.map { IngredientUI.fromDomain(it) },
                nutriments = product.nutriments?.let { NutrimentsUI.fromDomain(it) },
                nutritionGrade = product.nutritionGrade?.uppercase(),
                nutritionScore = product.nutritionScore,
                additivesCount = product.additivesN,
                additivesTags = product.additivesTags.map { it.replace(Regex("^[a-z]{2}:"), "") },
                allergens = product.allergensTags.map { it.replace(Regex("^[a-z]{2}:"), "") },
                categories = product.categoriesTags.map { it.replace(Regex("^[a-z]{2}:"), "") },
                quantity = product.quantity,
                servingSize = product.servingSize
            )
        }
    }
}