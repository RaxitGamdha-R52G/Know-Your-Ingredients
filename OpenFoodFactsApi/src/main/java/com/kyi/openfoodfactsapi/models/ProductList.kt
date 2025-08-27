package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class ProductList(
    val barcode: String,
    val key: String,
    val value: String
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "product" to barcode,
        "k" to key,
        "v" to value
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): ProductList = ProductList(
            barcode = json["product"] as String,
            key = json["k"] as String,
            value = json["v"] as String
        )
    }

    override fun toString(): String = toJson().toString()
}