package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class EcoscoreData(
    var grade: String? = null,
    var score: Double? = null,
    var status: EcoscoreStatus? = null,
    var agribalyse: Agribalyse? = null,
    var adjustments: EcoscoreAdjustments? = null,
    var missingDataWarning: Boolean = false
) : JsonObject() {

    override fun toJson(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        if (grade != null) map["grade"] = grade
        if (score != null) map["score"] = score
        if (status != null) map["status"] = status!!.name.lowercase()
        if (agribalyse != null) map["agribalyse"] = agribalyse!!.toJson()
        if (adjustments != null) map["adjustments"] = adjustments!!.toJson()
        map["missing_data_warning"] = missingDataWarning
        return map
    }

    companion object {

        fun fromJson(json: Map<String, Any?>): EcoscoreData = EcoscoreData(
            grade = json["grade"] as String?,
            score = parseDouble(json["score"]),
            status = json["status"]?.let { value ->
                EcoscoreStatus.entries.firstOrNull { it.name.lowercase() == value as String }
            },
            agribalyse = json["agribalyse"]?.let { Agribalyse.fromJson(it as Map<String, Any?>) },
            adjustments = json["adjustments"]?.let { EcoscoreAdjustments.fromJson(it as Map<String, Any?>) },
            missingDataWarning = if (json["missing_data_warning"] == null) false else parseBool(json["missing_data_warning"])
        )

        fun toJsonHelper(d: EcoscoreData?): Map<String, Any?>? = d?.toJson()
    }
}