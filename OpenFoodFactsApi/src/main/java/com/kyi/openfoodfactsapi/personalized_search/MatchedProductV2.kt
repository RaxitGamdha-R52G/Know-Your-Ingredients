package com.kyi.openfoodfactsapi.personalized_search

import com.kyi.openfoodfactsapi.models.Attribute
import com.kyi.openfoodfactsapi.models.Product

enum class MatchedProductStatusV2 {
    VERY_GOOD_MATCH,
    GOOD_MATCH,
    POOR_MATCH,
    UNKNOWN_MATCH,
    MAY_NOT_MATCH,
    DOES_NOT_MATCH
}

open class MatchedScoreV2(
    product: Product,
    productPreferencesManager: ProductPreferencesManager
) {

    val barcode = product.barcode!!

    protected var score = 0.0

    protected var status: MatchedProductStatusV2

    protected var debug = ""

    protected var initialOrder = 0

    init {
        score = 0.0
        debug = ""
        val attributeGroups = product.attributeGroups
        if (attributeGroups == null) {
            status = MatchedProductStatusV2.UNKNOWN_MATCH
            debug = "no attribute_groups"
            return
        }
        var sumOfFactors = 0
        var sumOfFactorsForUnknownAttributes = 0
        var mayNotMatch = false
        var doesNotMatch = false
        var isUnknown = false
        for (group in attributeGroups) {
            group.attributes?.forEach { attribute ->
                val attributeId = attribute.id!!
                val match = attribute.match ?: 0.0
                val importanceId =
                    productPreferencesManager.getImportanceIdForAttributeId(attributeId)
                if (importanceId == PreferenceImportance.ID_NOT_IMPORTANT) {
                    debug += "$attributeId $importanceId\n"
                    return@forEach
                }
                val factor = PREFERENCES_FACTORS[importanceId]!!
                sumOfFactors += factor
                if (attribute.status == Attribute.STATUS_UNKNOWN) {
                    sumOfFactorsForUnknownAttributes += factor
                    if (importanceId == PreferenceImportance.ID_MANDATORY) {
                        isUnknown = true
                    }
                } else {
                    score += match * factor
                    debug += "$attributeId $importanceId - match: $match\n"
                    if (importanceId == PreferenceImportance.ID_MANDATORY) {
                        if (match <= 10) {
                            doesNotMatch = true
                        } else if (match <= 50) {
                            mayNotMatch = true
                        }
                    }
                }
            }
        }
        if (sumOfFactors == 0) {
            score = 0.0
        } else {
            score /= sumOfFactors
        }
        if (doesNotMatch) {
            score = 0.0
            status = MatchedProductStatusV2.DOES_NOT_MATCH
        } else if (mayNotMatch) {
            status = MatchedProductStatusV2.MAY_NOT_MATCH
        } else if (isUnknown) {
            status = MatchedProductStatusV2.UNKNOWN_MATCH
        } else if (sumOfFactorsForUnknownAttributes >= sumOfFactors / 2) {
            status = MatchedProductStatusV2.UNKNOWN_MATCH
        } else if (score >= 75) {
            status = MatchedProductStatusV2.VERY_GOOD_MATCH
        } else if (score >= 50) {
            status = MatchedProductStatusV2.GOOD_MATCH
        } else {
            status = MatchedProductStatusV2.POOR_MATCH
        }
    }

    companion object {

        val PREFERENCES_FACTORS = mapOf(
            PreferenceImportance.ID_MANDATORY to 2,
            PreferenceImportance.ID_VERY_IMPORTANT to 2,
            PreferenceImportance.ID_IMPORTANT to 1,
            PreferenceImportance.ID_NOT_IMPORTANT to 0
        )

        fun sort(scores: List<MatchedScoreV2>) {
            var i = 0
            for (score in scores) {
                score.initialOrder = i++
            }
            scores.sortWith(compareByDescending { it.score }.thenByDescending { if (it.status == MatchedProductStatusV2.DOES_NOT_MATCH) 0 else 1 }
                .thenBy { it.initialOrder })
        }
    }
}

class MatchedProductV2(
    product: Product,
    productPreferencesManager: ProductPreferencesManager
) : MatchedScoreV2(product, productPreferencesManager) {

    val product: Product = product

    companion object {

        fun sort(
            products: List<Product>,
            productPreferencesManager: ProductPreferencesManager
        ): List<MatchedProductV2> {
            val scores = mutableListOf<MatchedProductV2>()
            for (product in products) {
                scores.add(MatchedProductV2(product, productPreferencesManager))
            }
            MatchedScoreV2.sort(scores)
            return scores
        }
    }
}