package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("code") val code: String,
    @SerialName("product_name") val productName: String,
    @SerialName("brands") val brands: String? = null,
    @SerialName("quantity") val quantity: String? = null,
    @SerialName("serving_size") val servingSize: String? = null,
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList(),
    @SerialName("nutriments") val nutriments: Nutriments? = null,
    @SerialName("nutrition_info") val nutritionInfo: NutritionInfo? = null,
    @SerialName("additives_info") val additivesInfo: AdditivesInfo? = null,
    @SerialName("allergens_info") val allergensInfo: AllergensInfo? = null,
    @SerialName("categories_info") val categoriesInfo: CategoriesInfo? = null,
    @SerialName("packagings") val packaging: List<Packaging> = emptyList(),
    @SerialName("labels_info") val labelsInfo: LabelsInfo? = null,
    @SerialName("ecoscore_info") val ecoScoreInfo: EcoScoreInfo? = null,
    @SerialName("image_info") val imageInfo: ImageInfo? = null
)