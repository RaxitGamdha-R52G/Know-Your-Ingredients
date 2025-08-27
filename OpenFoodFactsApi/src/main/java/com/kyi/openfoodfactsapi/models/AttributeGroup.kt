package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class AttributeGroup(
    val id: String? = null,
    val name: String? = null,
    val warning: String? = null,
    val attributes: List<Attribute>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = removeNullEntries(
        mapOf(
            JSON_TAG_ID to id,
            JSON_TAG_NAME to name,
            JSON_TAG_WARNING to warning,
            JSON_TAG_ATTRIBUTES to listToJson()
        )
    )

    private fun listToJson(): List<Map<String, Any?>>? {
        if (attributes == null || attributes!!.isEmpty()) {
            return null
        }
        val result = mutableListOf<Map<String, Any?>>()
        for (item in attributes!!) {
            result.add(item.toJson())
        }
        return result
    }

    companion object {
        private const val JSON_TAG_ID = "id"
        private const val JSON_TAG_NAME = "name"
        private const val JSON_TAG_WARNING = "warning"
        private const val JSON_TAG_ATTRIBUTES = "attributes"

        const val ATTRIBUTE_GROUP_NUTRITIONAL_QUALITY = "nutritional_quality"
        const val ATTRIBUTE_GROUP_PROCESSING = "processing"
        const val ATTRIBUTE_GROUP_ALLERGENS = "allergens"
        const val ATTRIBUTE_GROUP_INGREDIENT_ANALYSIS = "ingredients_analysis"
        const val ATTRIBUTE_GROUP_LABELS = "labels"
        const val ATTRIBUTE_GROUP_ENVIRONMENT = "environment"

        fun fromJson(json: Map<String, Any?>): AttributeGroup = AttributeGroup(
            id = json[JSON_TAG_ID] as String?,
            name = json[JSON_TAG_NAME] as String?,
            warning = json[JSON_TAG_WARNING] as String?,
            attributes = (json[JSON_TAG_ATTRIBUTES] as List<*>?)
                ?.map { Attribute.fromJson(it as Map<String, Any?>) }
        )
    }

    override fun toString(): String = "AttributeGroup(${toJson()})"
}