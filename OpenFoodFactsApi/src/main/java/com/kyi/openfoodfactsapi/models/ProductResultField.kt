package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class ProductResultField : JsonObject() {

    var id: String? = null

    var value: String? = null

    var defaultValue: String? = null

    var valuedConverted: String? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): ProductResultField = ProductResultField().apply {
            id = json["id"] as String?
            value = JsonHelper.stringFromJSON(json["value"])
            defaultValue = JsonHelper.stringFromJSON(json["default_value"])
            valuedConverted = JsonHelper.stringFromJSON(json["valued_converted"])
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "id" to id,
        "value" to value,
        "default_value" to defaultValue,
        "valued_converted" to valuedConverted
    )

    override fun toString(): String = toJson().toString()
}