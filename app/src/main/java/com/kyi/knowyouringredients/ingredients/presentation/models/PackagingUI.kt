package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Packaging

data class PackagingUI(
    val material: String,
    val quantity: String,
    val shape: String,
    val recycling: String,
    val weight: String
) {
    companion object {
        fun fromDomain(packaging: Packaging): PackagingUI {
            return PackagingUI(
                material = packaging.material?.replace(Regex("^[a-z]{2}:"), "") ?: "Unknown",
                quantity = "${packaging.numberOfUnits ?: "1"} x ${packaging.quantityValue ?: "0"} ${packaging.quantityUnit ?: "g"}",
                shape = packaging.shape?.replace(Regex("^[a-z]{2}:"), "") ?: "Unknown",
                recycling = packaging.recycling?.replace(Regex("^[a-z]{2}:"), "") ?: "N/A",
                weight = packaging.weightMeasured?.let { "$it g" } ?: "N/A"
            )
        }
    }
}