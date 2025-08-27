package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class InsightType(override val offTag: String) : OffTagged {
    INGREDIENT_SPELLCHECK("ingredient_spellcheck"),
    PACKAGER_CODE("packager_code"),
    LABEL("label"),
    CATEGORY("category"),
    PRODUCT_WEIGHT("product_weight"),
    EXPIRATION_DATE("expiration_date"),
    BRAND("brand"),
    STORE("store"),
    NUTRIENT("nutrient"),
    UNDEFINED("undefined"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun fromOffTag(offTag: String?): InsightType? =
            entries.firstOrNull { it.offTag == offTag } ?: UNDEFINED
    }
}