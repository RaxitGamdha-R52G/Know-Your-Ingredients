package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class ValueCount(
    val value: String,
    val productCount: Int
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "v" to value,
        "product_count" to productCount
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): ValueCount = ValueCount(
            value = json["v"] as String,
            productCount = (json["product_count"] as Number).toInt()
        )
    }

    override fun toString(): String = toJson().toString()
}