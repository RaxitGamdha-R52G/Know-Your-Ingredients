package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Nutriments

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
    val saltServing: String,
    val novaGroup: String,
    val carbonFootprint: String,
    val fruitsVeggiesNuts: String
) {
    companion object {
        fun fromDomain(nutriments: Nutriments?): NutrimentsUI {
            return NutrimentsUI(
                energyKcal100g = nutriments?.energyKcal100g?.let { "$it ${nutriments.energyKcalUnit ?: "kcal"}" }
                    ?: "N/A",
                energyKcalServing = nutriments?.energyKcalServing?.let { "$it ${nutriments.energyKcalUnit ?: "kcal"}" }
                    ?: "N/A",
                fat100g = nutriments?.fat100g?.let { "$it ${nutriments.fatUnit ?: "g"}" } ?: "N/A",
                fatServing = nutriments?.fatServing?.let { "$it ${nutriments.fatUnit ?: "g"}" }
                    ?: "N/A",
                saturatedFat100g = nutriments?.saturatedFat100g?.let { "$it ${nutriments.fatUnit ?: "g"}" }
                    ?: "N/A",
                saturatedFatServing = nutriments?.saturatedFatServing?.let { "$it ${nutriments.fatUnit ?: "g"}" }
                    ?: "N/A",
                carbohydrates100g = nutriments?.carbohydrates100g?.let { "$it ${nutriments.carbohydratesUnit ?: "g"}" }
                    ?: "N/A",
                carbohydratesServing = nutriments?.carbohydratesServing?.let { "$it ${nutriments.carbohydratesUnit ?: "g"}" }
                    ?: "N/A",
                sugars100g = nutriments?.sugars100g?.let { "$it ${nutriments.sugarsUnit ?: "g"}" }
                    ?: "N/A",
                sugarsServing = nutriments?.sugarsServing?.let { "$it ${nutriments.sugarsUnit ?: "g"}" }
                    ?: "N/A",
                proteins100g = nutriments?.proteins100g?.let { "$it ${nutriments.proteinsUnit ?: "g"}" }
                    ?: "N/A",
                proteinsServing = nutriments?.proteinsServing?.let { "$it ${nutriments.proteinsUnit ?: "g"}" }
                    ?: "N/A",
                salt100g = nutriments?.salt100g?.let { "$it ${nutriments.saltUnit ?: "g"}" }
                    ?: "N/A",
                saltServing = nutriments?.saltServing?.let { "$it ${nutriments.saltUnit ?: "g"}" }
                    ?: "N/A",
                novaGroup = nutriments?.novaGroup100g?.toString() ?: "N/A",
                carbonFootprint = nutriments?.carbonFootprint100g?.let { "$it g CO2e" } ?: "N/A",
                fruitsVeggiesNuts = nutriments?.fruitsVeggiesNuts100g?.let { "$it%" } ?: "N/A")
        }
    }
}