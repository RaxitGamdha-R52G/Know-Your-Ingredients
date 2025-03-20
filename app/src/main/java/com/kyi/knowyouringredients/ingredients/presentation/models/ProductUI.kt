package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Product

data class ProductUI(
    val code: String,
    val productName: String,
    val brands: List<String> = emptyList(),
    val packaging: List<PackagingUI> = emptyList(),
    val ingredients: List<IngredientUI> = emptyList(),          //ingredients_tags
    val nutriments: NutrimentsUI? = null,
    val nutritionGrade: String? = null,
    val nutritionScore: Int? = null,
) {
    companion object {
        // Factory function to convert Product to ProductUI
        fun fromDomain(product: Product): ProductUI {
            return ProductUI(
                code = product.code,
                productName = product.productName,
                brands = product.brands?.split(",")?.map { it.trim() } ?: emptyList(),
                packaging = product.packaging.map { PackagingUI.fromDomain(it) },
                ingredients = product.ingredients.map { IngredientUI.fromDomain(it) },
                nutriments = NutrimentsUI.fromDomain(product.nutriments),
                nutritionGrade = product.nutritionGrade,
                nutritionScore = product.nutritionScore
            )
        }
    }
}

//// Extension function to convert Product to ProductUI
//fun Product.toUI(): ProductUI {
//    return ProductUI(
//        code = this.code,
//        productName = this.productName,
//        brands = this.brands?.split(",")?.map { it.trim() }
//            ?: emptyList(), // Split brands into list
//        packaging = this.packaging.map { PackagingUI.fromDomain(it) },
//        ingredients = this.ingredients.map { IngredientUI.fromDomain(it) },
//        nutriments = NutrimentsUI.fromDomain(this.nutriments),
//        nutritionGrade = this.nutritionGrade,
//        nutritionScore = this.nutritionScore
//    )
//}