package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class EcoscoreAdjustments(
    var packaging: Packaging? = null,
    var originsOfIngredients: OriginsOfIngredients? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        if (packaging != null) map["packaging"] = packaging!!.toJson()
        if (originsOfIngredients != null) map["origins_of_ingredients"] = originsOfIngredients!!.toJson()
        return map
    }

    companion object {

        fun fromJson(json: Map<String, Any?>): EcoscoreAdjustments = EcoscoreAdjustments(
            packaging = if (json["packaging"] != null) Packaging.fromJson(json["packaging"] as Map<String, Any?>) else null,
            originsOfIngredients = if (json["origins_of_ingredients"] != null) OriginsOfIngredients.fromJson(json["origins_of_ingredients"] as Map<String, Any?>) else null
        )
    }
}