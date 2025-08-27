package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class NutrientLevel(override val offTag: String) : OffTagged {
    LOW("low"),
    MODERATE("moderate"),
    HIGH("high"),
    UNDEFINED("undefined");

    companion object {

        fun fromOffTag(offTag: String?): NutrientLevel? = entries.firstOrNull { it.offTag == offTag } ?: UNDEFINED
    }
}

val NutrientLevel?.value: String get() = this?.offTag ?: NutrientLevel.UNDEFINED.offTag

class NutrientLevels(val levels: Map<String, NutrientLevel>) {

    companion object {
        const val NUTRIENT_SUGARS = "sugars"
        const val NUTRIENT_FAT = "fat"
        const val NUTRIENT_SATURATED_FAT = "saturated-fat"
        const val NUTRIENT_SALT = "salt"

        val nutrients = listOf(NUTRIENT_SUGARS, NUTRIENT_FAT, NUTRIENT_SATURATED_FAT, NUTRIENT_SALT)

        fun fromJson(json: Map<*, *>?): NutrientLevels {
            val result = mutableMapOf<String, NutrientLevel>()

            if (json == null) {
                return NutrientLevels(result)
            }

            for (key in nutrients) {
                result[key] = NutrientLevel.fromOffTag(json[key] as String?) ?: NutrientLevel.UNDEFINED
            }

            return NutrientLevels(result)
        }

        fun toJson(nutrientLevels: NutrientLevels?): Map<String, Any>? {
            val result = mutableMapOf<String, String>()

            if (nutrientLevels == null) {
                return null
            }

            for (i in nutrientLevels.levels.indices) {
                val key = nutrients[i]
                result[key] = nutrientLevels.levels[key]?.value ?: NutrientLevel.UNDEFINED.value
            }

            return result
        }
    }
}