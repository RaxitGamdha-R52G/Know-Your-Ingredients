package com.kyi.openfoodfactsapi.personalized_search

import com.kyi.openfoodfactsapi.models.Attribute
import com.kyi.openfoodfactsapi.models.Product

enum class MatchedProductStatus {
    YES,
    NO,
    UNKNOWN
}

class MatchedProduct(
    val product: Product,
    productPreferencesManager: ProductPreferencesManager
) {

    private var status: MatchedProductStatus? = null

    private var score = 0.0

    private var debug = ""

    init {
        val attributeGroups = product.attributeGroups
        if (attributeGroups == null) {
            status = null
            return
        }
        status = MatchedProductStatus.YES
        for (group in attributeGroups) {
            group.attributes?.forEach { attribute ->
                val preferenceImportance =
                    productPreferencesManager.getPreferenceImportanceFromImportanceId(
                        productPreferencesManager.getImportanceIdForAttributeId(attribute.id!!)
                    )
                if (preferenceImportance != null) {
                    val importanceId = preferenceImportance.id
                    val factor = preferenceImportance.factor ?: 0
                    val minimalMatch = preferenceImportance.minimalMatch
                    if (importanceId == null || factor == 0) {
                        debug += "${attribute.id} $importanceId\n"
                    } else {
                        if (attribute.status == Attribute.STATUS_UNKNOWN) {
                            if (status == MatchedProductStatus.YES) {
                                status = MatchedProductStatus.UNKNOWN
                            }
                        } else {
                            debug += "${attribute.id} $importanceId - match: ${attribute.match}\n"
                            score += (attribute.match ?: 0) * factor
                            if (minimalMatch != null && (attribute.match ?: 0) <= minimalMatch) {
                                status = MatchedProductStatus.NO
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun sort(
            products: List<Product>,
            productPreferencesManager: ProductPreferencesManager
        ): List<MatchedProduct> {
            val result = mutableListOf<MatchedProduct>()
            for (product in products) {
                val matchedProduct = MatchedProduct(product, productPreferencesManager)
                result.add(matchedProduct)
            }
            result.sortByDescending { it.score }
            return result
        }
    }
}