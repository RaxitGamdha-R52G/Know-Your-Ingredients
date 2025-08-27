package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

class Insight(
    val id: String? = null,
    val type: InsightType? = null,
    val barcode: String? = null,
    val countries: List<Any?>? = null,
    val lang: String? = null,
    val model: String? = null,
    val confidence: Double? = null
) {

    companion object {

        fun fromJson(json: Any?): List<Insight>? {
            if (json == null) {
                return null
            }
            val result = mutableListOf<Insight>()
            for (jsonInsight in json as List<*>) {
                val map = jsonInsight as Map<String, Any?>
                val insightType = InsightType.fromOffTag(map["type"] as String?) ?: InsightType.UNDEFINED
                result.add(
                    Insight(
                        id = map["id"] as String?,
                        type = insightType,
                        barcode = map["barcode"] as String?,
                        countries = map["countries"] as List<Any?>?,
                        lang = map["lang"] as String?,
                        model = map["model"] as String?,
                        confidence = map["confidence"] as Double?
                    )
                )
            }
            return result
        }

        fun toJson(insights: List<Insight>?): List<Map<String, Any?>> {
            if (insights == null) {
                return emptyList()
            }
            val result = mutableListOf<Map<String, Any?>>()
            for (insight in insights) {
                val jsonInsight = mutableMapOf<String, Any?>()
                jsonInsight["id"] = insight.id
                jsonInsight["type"] = insight.type?.offTag
                jsonInsight["barcode"] = insight.barcode
                jsonInsight["countries"] = insight.countries
                jsonInsight["lang"] = insight.lang
                jsonInsight["model"] = insight.model
                jsonInsight["confidence"] = insight.confidence
                result.add(jsonInsight)
            }
            return result
        }
    }
}