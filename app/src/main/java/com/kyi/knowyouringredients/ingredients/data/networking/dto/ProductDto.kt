package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("code") val code: String? = null,
    @SerialName("product_name") val productName: String? = null,
    @SerialName("brands") val brands: String? = null,
    @SerialName("packagings") val packagings: List<PackagingDto>? = null,
    @SerialName("ingredients") val ingredients: List<IngredientDto>? = null,
    @SerialName("nutriments") val nutriments: NutrimentsDto? = null,
    @SerialName("nutrition_grade_fr") val nutritionGrade: String? = null,
    @SerialName("nutrition_score_fr") val nutritionScore: Int? = null,
    @SerialName("nova_group") val novaGroup: Int? = null,
    @SerialName("nutrient_levels") val nutrientLevels: Map<String, String>? = null,
    @SerialName("additives_n") val additivesN: Int? = null,
    @SerialName("additives_tags") val additivesTags: List<String>? = null,
    @SerialName("allergens_tags") val allergensTags: List<String>? = null,
    @SerialName("categories_tags") val categoriesTags: List<String>? = null,
    @SerialName("quantity") val quantity: String? = null,
    @SerialName("serving_size") val servingSize: String? = null
)