package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class OrderedNutrients(val nutrients: List<OrderedNutrient>) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "nutrients" to nutrients
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): OrderedNutrients = OrderedNutrients(
            nutrients = (json["nutrients"] as List<*>).map { OrderedNutrient.fromJson(it as Map<String, Any?>) }
        )
    }
}