package com.kyi.knowyouringredients.ingredients.data.networking.dto

import com.kyi.knowyouringredients.ingredients.domain.models.Ingredient
import com.kyi.knowyouringredients.ingredients.domain.models.Nutriments
import com.kyi.knowyouringredients.ingredients.domain.models.Packaging
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("code") val code: String,
    @SerialName("product_name") val productName: String,
    @SerialName("brands") val brands: String? = null,
    @SerialName("packagings") val packaging: List<Packaging> = emptyList(),
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList(),
    @SerialName("nutriments") val nutriments: Nutriments? = null,
    @SerialName("nutrition_grade_fr") val nutritionGrade: String? = null,
    @SerialName("nutrition_score_fr") val nutritionScore: Int? = null,
    @SerialName("nova_group") val novaGroup: Int? = null,
    @SerialName("nutrient_levels") val nutrientLevels: Map<String, String>? = null,
    @SerialName("additives_n") val additivesN: Int? = null,
    @SerialName("additives_tags") val additivesTags: List<String> = emptyList(),
    @SerialName("allergens_tags") val allergensTags: List<String> = emptyList(),
    @SerialName("categories_tags") val categoriesTags: List<String> = emptyList(),
    @SerialName("quantity") val quantity: String? = null,
    @SerialName("serving_size") val servingSize: String? = null
)