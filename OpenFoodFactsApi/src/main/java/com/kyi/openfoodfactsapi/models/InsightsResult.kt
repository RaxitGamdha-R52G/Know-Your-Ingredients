package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class InsightsResult(
    val status: String? = null,
    val insights: List<Insight>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "status" to status,
        "insights" to Insight.toJson(insights)
    )

    companion object {

        fun fromJson(json: Any?): InsightsResult {
            val map = json as Map<String, Any?>
            return InsightsResult(
                status = map["status"] as String?,
                insights = Insight.fromJson(map["insights"])
            )
        }
    }
}