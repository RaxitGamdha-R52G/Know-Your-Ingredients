package com.kyi.openfoodfactsapi.models

import NutrimentsHelper
import com.kyi.openfoodfactsapi.sources.JsonObject

class Nutriments : JsonObject() {

    private val map = mutableMapOf<String, Double?>()

    constructor()

    private constructor(map: Map<String, Any?>) {
        for (nutrient in Nutrient.entries) {
            for (perSize in PerSize.entries) {
                val tag = getTag(nutrient, perSize)
                try {
                    val value = parseDouble(map[tag])
                    if (value != null) {
                        this.map[tag] = value
                    } else if (map.containsKey(tag)) {
                        this.map[tag] = null
                    }
                } catch (e: Exception) {
                    throw Exception("Could not parse the value for $nutrient and $perSize: ${map[tag]}")
                }
            }
        }
    }

    private fun getTag(nutrient: Nutrient, perSize: PerSize): String =
        "${nutrient.offTag}_${perSize.offTag}"

    fun getValue(nutrient: Nutrient, perSize: PerSize): Double? = map[getTag(nutrient, perSize)]

    fun setValue(nutrient: Nutrient, perSize: PerSize, value: Double?): Nutriments {
        map[getTag(nutrient, perSize)] = value
        return this
    }

    fun isEmpty(isNullEmpty: Boolean = false): Boolean {
        if (map.isEmpty()) return true
        if (!isNullEmpty) return false
        for (value in map.values) {
            if (value != null) return false
        }
        return true
    }

    fun getComputedKJ(perSize: PerSize): Double? {
        var result = getValue(Nutrient.ENERGY_KJ, perSize)
        if (result != null) return result
        result = getValue(Nutrient.ENERGY_KCAL, perSize)
        if (result != null) return NutrimentsHelper.fromKCalToKJ(result)
        return null
    }

    companion object {

        fun fromJson(json: Map<String, Any?>): Nutriments = Nutriments(json)

        fun toJsonHelper(n: Nutriments?): Map<String, Any?> = n?.toJson() ?: emptyMap()
    }

    override fun toJson(): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        for (nutrient in Nutrient.entries) {
            for (perSize in PerSize.entries) {
                val tag = getTag(nutrient, perSize)
                val value = map[tag]
                if (value != null) {
                    result[tag] = value
                } else if (map.containsKey(tag)) {
                    result[tag] = ""
                }
            }
        }
        return result
    }
}