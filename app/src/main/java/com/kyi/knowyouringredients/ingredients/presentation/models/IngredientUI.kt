package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.Ingredient
import kotlinx.serialization.Serializable

@Serializable
data class IngredientUI(
    val id: String,
    val name: String,
    val isVegan: String,
    val isVegetarian: String,
    val percent: String,
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
                percent = ingredient.percentEstimate?.toDisplayableNumber()?.formatted?.let { "$it%" }
                    ?: "N/A",
                subIngredients = ingredient.subIngredients.map { fromDomain(it) }
            )
        }
    }
}