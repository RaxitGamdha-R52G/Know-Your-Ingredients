package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductUI(
    val code: String,
    val productName: String,
    val brands: List<String> = emptyList(),
    val quantity: String? = null,
    val servingSize: String? = null,

    // Ingredients fields
    val ingredients: List<IngredientUI> = emptyList(),

    // Nutriments fields
    val energyKcal100g: String,
    val energyKcalServing: String,
    val fat100g: String,
    val fatServing: String,
    val saturatedFat100g: String,
    val saturatedFatServing: String,
    val carbohydrates100g: String,
    val carbohydratesServing: String,
    val sugars100g: String,
    val sugarsServing: String,
    val proteins100g: String,
    val proteinsServing: String,
    val salt100g: String,
    val saltServing: String,

    // Nutrition Info fields
    val nutritionGrade: String,
    val nutritionScore: String,
    val nutrientLevels: Map<String, String>,
    val novaGroup: String,

    // Additives Info fields
    val additivesCount: String,
    val additivesTags: List<String>,

    // Allergens Info fields
    val allergensTags: List<String>,

    // Categories Info fields
    val categoriesTags: List<String>,

    // Packaging fields
    val packaging: List<PackagingUI> = emptyList(),

    // Labels Info fields
    val labelsTags: List<String>,

    // EcoScore Info fields
    val ecoScoreGrade: String,
    val ecoScoreScore: String,

    // Image Info fields
    val mainImageUrl: String?,
    val smallImageUrl: String?,
    val frontThumbUrl: String?,
    val imageUrl: String?
) {
    companion object {
        fun fromDomain(product: Product): ProductUI {
            return ProductUI(
                code = product.code,
                productName = product.productName.ifEmpty { "Unknown Product" },
                brands = product.brands?.split(",")?.map { it.trim() } ?: emptyList(),
                quantity = product.quantity,
                servingSize = product.servingSize,

                // Ingredients fields
                ingredients = product.ingredients.map { IngredientUI.fromDomain(it) },

                // Nutriments fields
                energyKcal100g = product.energyKcal100g?.toDisplayableNumber()?.formatted?.let { "$it kcal" }
                    ?: "N/A",
                energyKcalServing = product.energyKcalServing?.toDisplayableNumber()?.formatted?.let { "$it kcal" }
                    ?: "N/A",
                fat100g = product.fat100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                fatServing = product.fatServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                saturatedFat100g = product.saturatedFat100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                saturatedFatServing = product.saturatedFatServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                carbohydrates100g = product.carbohydrates100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                carbohydratesServing = product.carbohydratesServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                sugars100g = product.sugars100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                sugarsServing = product.sugarsServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                proteins100g = product.proteins100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                proteinsServing = product.proteinsServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                salt100g = product.salt100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                saltServing = product.saltServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",

                // Nutrition Info fields
                nutritionGrade = product.nutritionGrade?.uppercase() ?: "U",
                nutritionScore = product.nutritionScore?.toString() ?: "N/A",
                nutrientLevels = product.nutrientLevels ?: emptyMap(),
                novaGroup = product.novaGroup?.toString() ?: "N/A",

                // Additives Info fields
                additivesCount = product.additivesCount?.toString() ?: "N/A",
                additivesTags = product.additivesTags.map { it.replace(Regex("^[a-z]{2}:"), "") },

                // Allergens Info fields
                allergensTags = product.allergensTags.map { it.replace(Regex("^[a-z]{2}:"), "") },

                // Categories Info fields
                categoriesTags = product.categoriesTags.map { it.replace(Regex("^[a-z]{2}:"), "") },

                // Packaging fields
                packaging = product.packaging.map { PackagingUI.fromDomain(it) },

                // Labels Info fields
                labelsTags = product.labelsTags.map { it.replace(Regex("^[a-z]{2}:"), "") },

                // EcoScore Info fields
                ecoScoreGrade = product.ecoScoreGrade?.uppercase() ?: "U",
                ecoScoreScore = product.ecoScoreScore?.toString() ?: "N/A",

                // Image Info fields
                mainImageUrl = product.mainImageUrl,
                smallImageUrl = product.smallImageUrl,
                frontThumbUrl = product.frontThumbUrl,
                imageUrl = null
            )
        }
    }
}