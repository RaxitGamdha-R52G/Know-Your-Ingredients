package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class Agribalyse(var score: Double? = null) : JsonObject() {

    override fun toJson(): Map<String, Any?> = removeNullEntries(
        mapOf(
            "score" to score
        )
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): Agribalyse =
            Agribalyse(
                score = parseDouble(json["score"])
            )
    }
}