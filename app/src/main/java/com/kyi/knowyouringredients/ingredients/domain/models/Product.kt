package com.kyi.knowyouringredients.ingredients.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("code") val code: String,                                       // Barcode (e.g., "3017620422003")
    @SerialName("product_name") val productName: String,                        // Product name (e.g., "Nutella")
    @SerialName("brands") val brands: String? = null,                           // Brands (e.g., "Nutella,Ferrero")
    @SerialName("packaging") val packaging: List<Packaging> = emptyList(),      // Expanded Packaging list
    @SerialName("ingredients") val ingredients: List<Ingredient> = emptyList(), // Expanded Ingredient list
    @SerialName("nutriments") val nutriments: Nutriments? = null,               // Expanded Nutriments
    @SerialName("nutrition_grade_fr") val nutritionGrade: String? = null,       // Nutrition grade (e.g., "e")
    @SerialName("nutrition_score_fr") val nutritionScore: Int? = null,          // Nutrition score (e.g., 26)
    @SerialName("additives_n") val additivesN: Int? = null,                     // Number of additives (e.g., 2)
    @SerialName("additives_tags") val additivesTags: List<String> = emptyList(),// Additives (e.g., ["en:e322", "en:e322i"])
    @SerialName("allergens_tags") val allergensTags: List<String> = emptyList(),// Allergens (e.g., ["en:milk", "en:nuts"])
    @SerialName("categories_tags") val categoriesTags: List<String> = emptyList(), // Categories (e.g., ["en:breakfasts", ...])
    @SerialName("quantity") val quantity: String? = null,                       // Total quantity (e.g., "400 g")
    @SerialName("serving_size") val servingSize: String? = null                 // Serving size (e.g., "15 g")
)