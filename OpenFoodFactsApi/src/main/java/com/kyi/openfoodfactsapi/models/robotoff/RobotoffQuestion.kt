package com.kyi.openfoodfactsapi.models.robotoff

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.models.InsightType

class RobotoffQuestion(
    val barcode: String? = null,
    val type: String? = null,
    val value: String? = null,
    val question: String? = null,
    val insightId: String? = null,
    val insightType: InsightType? = null,
    val imageUrl: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "barcode" to barcode,
        "type" to type,
        "value" to value,
        "question" to question,
        "insight_id" to insightId,
        "insight_type" to insightType?.offTag,
        "source_image_url" to imageUrl
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffQuestion = RobotoffQuestion(
            barcode = json["barcode"] as String?,
            type = json["type"] as String?,
            value = json["value"] as String?,
            question = json["question"] as String?,
            insightId = json["insight_id"] as String?,
            insightType = InsightType.Companion.fromOffTag(json["insight_type"] as String?),
            imageUrl = json["source_image_url"] as String?
        )
    }
}