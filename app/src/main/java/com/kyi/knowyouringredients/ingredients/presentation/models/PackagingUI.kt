package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.models.Packaging

data class PackagingUI(
    val material: String,
    val numberOfUnits: String,
    val shape: String,
    val recycling: String
) {
    companion object {
        fun fromDomain(packaging: Packaging): PackagingUI {
            return PackagingUI(
                material = packaging.material?.replace(Regex("^[a-z]{2}:"), "") ?: "Unknown",
                numberOfUnits = packaging.numberOfUnits ?: "1",
                shape = packaging.shape?.replace(Regex("^[a-z]{2}:"), "") ?: "Unknown",
                recycling = packaging.recycling?.replace(Regex("^[a-z]{2}:"), "") ?: "N/A"
            )
        }
    }
}