package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.Ingredient

data class IngredientUI(
    val id: String,
    val name: String,
    val isVegan: String,
    val isVegetarian: String,
    val percent: String,
    val fromPalmOil: String,
    val subIngredients: List<IngredientUI> = emptyList()
) {
    companion object {
        fun fromDomain(ingredient: Ingredient): IngredientUI {
            return IngredientUI(
                id = ingredient.id.replace(Regex("^[a-z]{2}:"), ""),
                name = ingredient.name,
                isVegan = ingredient.isVegan?.replaceFirstChar { it.uppercase() } ?: "Unknown",
                isVegetarian = ingredient.isVegetarian?.replaceFirstChar { it.uppercase() }
                    ?: "Unknown",
                percent = ingredient.percent?.let { "$it%" }
                    ?: ingredient.percentEstimate?.let { "$it%" } ?: "N/A",
                fromPalmOil = ingredient.fromPalmOil?.let { if (it == "yes") "Yes" else "No" }
                    ?: "No",
                subIngredients = ingredient.subIngredients.map { fromDomain(it) }
            )
        }
    }
}