package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged


enum class NutrientModifier(override val offTag: String) : OffTagged {
    APPROXIMATELY("~"),
    GREATER_THAN(">"),
    LESS_THAN("<");

    companion object {

        fun fromOffTag(offTag: String?): NutrientModifier? = entries.firstOrNull { it.offTag == offTag }

        fun fromValue(value: String?): NutrientModifier? {
            if (value == null || value.isEmpty()) {
                return null
            }

            return fromOffTag(value.substring(0, 1))
        }
    }
}