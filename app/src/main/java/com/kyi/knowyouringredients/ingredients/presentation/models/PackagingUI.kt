package com.kyi.knowyouringredients.ingredients.presentation.models

import com.kyi.knowyouringredients.ingredients.domain.Packaging

data class PackagingUI(
    val material: String,   // Material type (e.g., "Glass", "Plastic")
    val quantity: String,   // Combined quantity and unit (e.g., "1.0 g")
    val shape: String       // Shape with default fallback (e.g., "Unknown")
) {
    companion object {
        // Factory function to create PackagingUI from domain Packaging
        fun fromDomain(packaging: Packaging): PackagingUI {
            return PackagingUI(
                material = packaging.material,
                quantity = "${packaging.quantityValue} ${packaging.quantityUnit}",
                shape = packaging.shape ?: "Unknown"
            )
        }
    }
}