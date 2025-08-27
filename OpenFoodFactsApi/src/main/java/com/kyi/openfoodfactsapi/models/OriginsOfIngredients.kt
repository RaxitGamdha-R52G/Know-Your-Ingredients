package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class OriginsOfIngredients(
    var epiScore: Double? = null,
    var epiValue: Double? = null,
    var transportationScore: Double? = null,
    var transportationValue: Double? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "epi_score" to epiScore,
        "epi_value" to epiValue,
        "transportation_score" to transportationScore,
        "transportation_value" to transportationValue
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): OriginsOfIngredients = OriginsOfIngredients(
            epiScore = parseDouble(json["epi_score"]),
            epiValue = parseDouble(json["epi_value"]),
            transportationScore = parseDouble(json["transportation_score"]),
            transportationValue = parseDouble(json["transportation_value"])
        )
    }
}