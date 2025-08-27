package com.kyi.openfoodfactsapi.sources

import kotlin.collections.iterator

/**
 * Abstract class for all serializable JSON model objects.
 */
abstract class JsonObject {

    abstract fun toJson(): Map<String, Any?>

    fun toData(): Map<String, String> {
        return toDataStatic(toJson())
    }

    companion object {

        fun toDataStatic(json: Map<String, Any?>): Map<String, String> {
            val result = mutableMapOf<String, String>()
            for ((key, value) in json) {
                result[key] = "$value"
            }
            return result
        }

        fun parseInt(json: Any?): Int? {
            return when (json) {
                is String -> json.toIntOrNull()
                is Double -> json.toInt()
                is Int -> json
                else -> null
            }
        }

        fun parseDouble(json: Any?): Double? {
            return when (json) {
                is String -> json.toDoubleOrNull()
                is Int -> json.toDouble()
                is Double -> json
                else -> null
            }
        }

        fun parseBool(json: Any?): Boolean {
            return when (json) {
                is String -> json == "1"
                is Int -> json == 1
                is Boolean -> json
                else -> false
            }
        }

        fun removeNullEntries(input: Map<String, Any?>): Map<String, Any?> {
            val result = mutableMapOf<String, Any?>()
            for ((key, value) in input) {
                if (value != null) {
                    result[key] = value
                }
            }
            return result
        }

        fun toValueStringStatic(json: Map<String, Any?>): String {
            var result = ""
            for ((_, value) in json) {
                if (value != null) {
                    result += " - $value"
                }
            }
            return result
        }
    }

    fun toValueString(): String {
        return toValueStringStatic(toJson())
    }
}