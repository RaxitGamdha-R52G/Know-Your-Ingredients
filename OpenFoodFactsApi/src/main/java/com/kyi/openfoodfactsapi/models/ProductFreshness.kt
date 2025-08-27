package com.kyi.openfoodfactsapi.models

import ProductImprovement

class ProductFreshness private constructor(
    val isEcoscoreReady: Boolean,
    val isNutriscoreReady: Boolean,
    val isIngredientsReady: Boolean,
    val lastModified: DateTime?,
    val improvements: Set<ProductImprovement>
) {

    companion object {

        fun fromProduct(product: Product): ProductFreshness = ProductFreshness(
            isEcoscoreReady = product.ecoscoreScore != null,
            isNutriscoreReady = product.nutriscore != null,
            isIngredientsReady = product.ingredientsTags != null && product.ingredientsTags!!.isNotEmpty(),
            lastModified = product.lastModified,
            improvements = product.getProductImprovements()
        )
    }

    override fun toString(): String = "ProductFreshness(" +
            "ecoscore:$isEcoscoreReady" +
            "," +
            "nutriscore:$isNutriscoreReady" +
            "," +
            "ingredients:$isIngredientsReady" +
            "," +
            "lastModified:$lastModified" +
            "," +
            "improvements:$improvements" +
            ")"
}