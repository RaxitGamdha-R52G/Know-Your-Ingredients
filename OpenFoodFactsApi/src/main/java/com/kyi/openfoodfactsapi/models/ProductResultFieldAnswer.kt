package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class ProductResultFieldAnswer : JsonObject() {

    var field: ProductResultField? = null

    var impact: LocalizedTag? = null

    var message: LocalizedTag? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): ProductResultFieldAnswer = ProductResultFieldAnswer().apply {
            field = json["field"]?.let { ProductResultField.fromJson(it as Map<String, Any?>) }
            impact = json["impact"]?.let { LocalizedTag.fromJson(it as Map<String, Any?>) }
            message = json["message"]?.let { LocalizedTag.fromJson(it as Map<String, Any?>) }
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "field" to field,
        "impact" to impact,
        "message" to message
    )

    override fun toString(): String = toJson().toString()
}