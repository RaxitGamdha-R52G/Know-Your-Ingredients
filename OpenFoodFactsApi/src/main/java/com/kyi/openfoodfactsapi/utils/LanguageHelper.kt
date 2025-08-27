package com.kyi.openfoodfactsapi.utils

object LanguageHelper {

    fun toJson(language: OpenFoodFactsLanguage?): String = language?.code ?: "-"

    fun fromJson(code: String?): OpenFoodFactsLanguage = OpenFoodFactsLanguage.fromOffTag(code) ?: OpenFoodFactsLanguage.UNDEFINED

    fun <T> toJsonMap(map: Map<OpenFoodFactsLanguage, T>?): Map<String, T>? = map?.mapKeys { it.key.offTag }

    fun toJsonStringMap(map: Map<OpenFoodFactsLanguage, String>?): Map<String, String>? = toJsonMap(map)

    fun fromJsonStringMap(map: Any?): Map<OpenFoodFactsLanguage, String>? {
        if (map == null) return null
        if (map !is Map<*, *>) throw Exception("Expected type: Map<String, String>")
        val result = mutableMapOf<OpenFoodFactsLanguage, String>()
        for ((key, value) in map) {
            result[fromJson(key as String)] = value as String
        }
        return result
    }

    fun fromJsonStringMapList(map: Any?): Map<OpenFoodFactsLanguage, List<String>>? {
        if (map == null) return null
        if (map !is Map<*, *>) throw Exception("Expected type: Map<String, List<String>>: $map")
        val result = mutableMapOf<OpenFoodFactsLanguage, List<String>>()
        for ((key, value) in map) {
            val list = mutableListOf<String>()
            for (item in value as List<*>) {
                list.add(item as String)
            }
            result[fromJson(key as String)] = list
        }
        return result
    }

    fun fromJsonStringMapIsoUnique(map: Any?): String? {
        if (map == null) return null
        if (map !is Map<*, *>) throw Exception("Expected type: Map<String, String>")
        for (value in map.values) {
            return value as String
        }
        return null
    }

    fun fromJsonStringMapIsoList(map: Any?): List<OpenFoodFactsLanguage>? {
        if (map == null) return null
        if (map !is Map<*, *>) throw Exception("Expected type: Map<String, String>")
        val result = mutableListOf<OpenFoodFactsLanguage>()
        for (value in map.values) {
            val list = value as String
            if (list.isEmpty()) continue
            list.split(",").forEach { result.add(fromJson(it)) }
        }
        return result
    }

    fun toJsonStringsListMap(map: Map<OpenFoodFactsLanguage, List<String>>?): Map<String, List<String>>? = toJsonMap(map)

    fun fromJsonStringsListMap(map: Any?): Map<OpenFoodFactsLanguage, List<String>>? {
        if (map == null) return null
        if (map !is Map<*, *>) throw Exception("Expected type: Map<String, List<String>>: $map")
        val result = mutableMapOf<OpenFoodFactsLanguage, List<String>>()
        for ((key, value) in map) {
            result[fromJson(key as String)] = (value as List<*>).map { it as String }
        }
        return result
    }
}