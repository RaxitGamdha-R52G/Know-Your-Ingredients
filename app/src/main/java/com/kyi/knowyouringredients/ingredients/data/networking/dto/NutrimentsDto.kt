package com.kyi.knowyouringredients.ingredients.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NutrimentsDto(
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
    @SerialName("energy-kcal_unit") val energyKcalUnit: String? = null,
    @SerialName("fat_unit") val fatUnit: String? = null,
    @SerialName("carbohydrates_unit") val carbohydratesUnit: String? = null,
    @SerialName("sugars_unit") val sugarsUnit: String? = null,
    @SerialName("proteins_unit") val proteinsUnit: String? = null,
    @SerialName("salt_unit") val saltUnit: String? = null,
    @SerialName("nutrition-score-fr_100g") val nutritionScoreFr100g: Int? = null,
    @SerialName("carbon-footprint-from-known-ingredients_100g") val carbonFootprint100g: Double? = null,
    @SerialName("fruits-vegetables-nuts-estimate-from-ingredients_100g") val fruitsVeggiesNuts100g: Double? = null
)