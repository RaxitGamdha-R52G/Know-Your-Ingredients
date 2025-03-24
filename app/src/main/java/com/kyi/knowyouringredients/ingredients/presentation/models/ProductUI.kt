package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.Product

data class ProductUI(
    val code: String,
    val productName: String,
    val brands: List<String> = emptyList(),
    val packaging: List<PackagingUI> = emptyList(),
    val ingredients: List<IngredientUI> = emptyList(),
    val nutriments: NutrimentsUI? = null,
    val nutritionInfo: NutritionInfoUI? = null,
    val additivesInfo: AdditivesInfoUI? = null,
    val allergensInfo: AllergensInfoUI? = null,
    val categoriesInfo: CategoriesInfoUI? = null,
    val quantity: String? = null,
    val servingSize: String? = null,
    val labelsInfo: LabelsInfoUI? = null,
    val ecoScoreInfo: EcoScoreInfoUI? = null,
    val imageInfo: ImageInfoUI? = null
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
                nutritionInfo = product.nutritionInfo?.let { NutritionInfoUI.fromDomain(it) },
                additivesInfo = product.additivesInfo?.let { AdditivesInfoUI.fromDomain(it) },
                allergensInfo = product.allergensInfo?.let { AllergensInfoUI.fromDomain(it) },
                categoriesInfo = product.categoriesInfo?.let { CategoriesInfoUI.fromDomain(it) },
                quantity = product.quantity,
                servingSize = product.servingSize,
                labelsInfo = product.labelsInfo?.let { LabelsInfoUI.fromDomain(it) },
                ecoScoreInfo = product.ecoScoreInfo?.let { EcoScoreInfoUI.fromDomain(it) },
                imageInfo = product.imageInfo?.let { ImageInfoUI.fromDomain(it) }
            )
        }
    }
}