package com.kyi.openfoodfactsapi.models.robotoff

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.models.Nutrient
import com.kyi.openfoodfactsapi.models.PerSize

class RobotoffNutrientExtractionResult(
    val status: String? = null,
    val count: Int? = null,
    val insights: List<RobotoffNutrientExtractionInsight>? = null
) : JsonObject() {

    private var latestInsightField: RobotoffNutrientExtractionInsight? = null

    val latestInsight: RobotoffNutrientExtractionInsight? get() {
        if (latestInsightField != null) return latestInsightField

        insights?.sortedWith(compareBy { it.completedAt })
        latestInsightField = insights?.last()

        return latestInsightField
    }

    fun getNutrientEntity(nutrient: Nutrient, perSize: PerSize): RobotoffNutrientEntity? =
        latestInsight?.data?.nutrients?.get(nutrient.getOffTagPerSize(perSize))

    fun getNutrientAnnotation(nutrient: Nutrient): RobotoffNutrientAnnotationData? =
        latestInsight?.data?.annotation?.nutrients?.get(nutrient.offTag)

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffNutrientExtractionResult = RobotoffNutrientExtractionResult(
            status = json["status"] as String?,
            count = (json["count"] as Number?)?.toInt(),
            insights = (json["insights"] as List<*>?)?.map { RobotoffNutrientExtractionInsight.fromJson(it as Map<String, Any?>) }
        )
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "status" to status,
        "count" to count,
        "insights" to insights
    )
}

