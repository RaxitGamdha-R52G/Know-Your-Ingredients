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

    // Ingredients fields
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList(),

    // Nutriments fields
    @SerialName("energy-kcal_100g") val energyKcal100g: Double? = null,
    @SerialName("fat_100g") val fat100g: Double? = null,
    @SerialName("saturated-fat_100g") val saturatedFat100g: Double? = null,
    @SerialName("carbohydrates_100g") val carbohydrates100g: Double? = null,
    @SerialName("sugars_100g") val sugars100g: Double? = null,
    @SerialName("proteins_100g") val proteins100g: Double? = null,
    @SerialName("salt_100g") val salt100g: Double? = null,
    @SerialName("energy-kcal_serving") val energyKcalServing: Double? = null,
    @SerialName("fat_serving") val fatServing: Double? = null,
    @SerialName("saturated-fat_serving") val saturatedFatServing: Double? = null,
    @SerialName("carbohydrates_serving") val carbohydratesServing: Double? = null,
    @SerialName("sugars_serving") val sugarsServing: Double? = null,
    @SerialName("proteins_serving") val proteinsServing: Double? = null,
    @SerialName("salt_serving") val saltServing: Double? = null,

    // Nutrition Info fields
    @SerialName("nutrition_grade_fr") val nutritionGrade: String? = null,
    @SerialName("nutrition_score_fr") val nutritionScore: Int? = null,
    @SerialName("nutrient_levels") val nutrientLevels: Map<String, String>? = null,
    @SerialName("nova_group") val novaGroup: Int? = null,

    // Additives Info fields
    @SerialName("additives_n") val additivesCount: Int? = null,
    @SerialName("additives_tags") val additivesTags: List<String> = emptyList(),

    // Allergens Info fields
    @SerialName("allergens_tags") val allergensTags: List<String> = emptyList(),

    // Categories Info fields
    @SerialName("categories_tags") val categoriesTags: List<String> = emptyList(),

    // Packaging fields
    @SerialName("packagings") val packaging: List<Packaging> = emptyList(),

    // Labels Info fields
    @SerialName("labels_tags") val labelsTags: List<String> = emptyList(),

    // EcoScore Info fields
    @SerialName("ecoscore_grade") val ecoScoreGrade: String? = null,
    @SerialName("ecoscore_score") val ecoScoreScore: Int? = null,

    // Image Info fields
    @SerialName("image_url") val mainImageUrl: String? = null,
    @SerialName("image_small_url") val smallImageUrl: String? = null,
    @SerialName("image_thumb_url") val frontThumbUrl: String? = null
)