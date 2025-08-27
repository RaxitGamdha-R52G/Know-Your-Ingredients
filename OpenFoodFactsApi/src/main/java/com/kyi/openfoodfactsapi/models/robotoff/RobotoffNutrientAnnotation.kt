package com.kyi.openfoodfactsapi.models.robotoff

import com.kyi.openfoodfactsapi.models.PerSize

class RobotoffNutrientAnnotation(
    val nutrients: Map<String, RobotoffNutrientAnnotationData>? = null,
    val servingSize: String? = null,
    val perSize: PerSize? = null
) {

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffNutrientAnnotation = RobotoffNutrientAnnotation(
            nutrients = (json["nutrients"] as Map<String, Any?>?)?.mapValues { RobotoffNutrientAnnotationData.fromJson(it.value as Map<String, Any?>) },
            servingSize = json["serving_size"] as String?,
            perSize = PerSize.Companion.fromOffTag(json["nutrition_data_per"] as String?)
        )
    }

    fun toJson(): Map<String, Any?> = mapOfNotNull(
        "nutrients" to nutrients,
        "serving_size" to servingSize,
        "nutrition_data_per" to perSize?.offTag
    )
}