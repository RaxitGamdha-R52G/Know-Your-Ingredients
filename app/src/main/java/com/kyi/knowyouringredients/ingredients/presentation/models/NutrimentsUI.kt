package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.Nutriments

data class NutrimentsUI(
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
    val saltServing: String
) {
    companion object {
        fun fromDomain(nutriments: Nutriments?): NutrimentsUI {
            return NutrimentsUI(
                energyKcal100g = nutriments?.energyKcal100g?.toDisplayableNumber()?.formatted?.let { "$it kcal" }
                    ?: "N/A",
                energyKcalServing = nutriments?.energyKcalServing?.toDisplayableNumber()?.formatted?.let { "$it kcal" }
                    ?: "N/A",
                fat100g = nutriments?.fat100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                fatServing = nutriments?.fatServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                saturatedFat100g = nutriments?.saturatedFat100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                saturatedFatServing = nutriments?.saturatedFatServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                carbohydrates100g = nutriments?.carbohydrates100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                carbohydratesServing = nutriments?.carbohydratesServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                sugars100g = nutriments?.sugars100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                sugarsServing = nutriments?.sugarsServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                proteins100g = nutriments?.proteins100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                proteinsServing = nutriments?.proteinsServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                salt100g = nutriments?.salt100g?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A",
                saltServing = nutriments?.saltServing?.toDisplayableNumber()?.formatted?.let { "$it g" }
                    ?: "N/A"
            )
        }
    }
}