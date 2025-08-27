package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class Packaging(var value: Double? = null, var score: Double? = null) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "value" to value,
        "score" to score
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): Packaging = Packaging(
            value = parseDouble(json["value"]),
            score = parseDouble(json["score"])
        )
    }
}