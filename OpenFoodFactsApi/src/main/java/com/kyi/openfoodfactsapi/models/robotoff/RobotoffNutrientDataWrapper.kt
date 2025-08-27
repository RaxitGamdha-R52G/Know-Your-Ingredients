package com.kyi.openfoodfactsapi.models.robotoff

import com.kyi.openfoodfactsapi.models.Nutrient
import com.kyi.openfoodfactsapi.models.PerSize

class RobotoffNutrientDataWrapper(
    val entities: Map<String, List<RobotoffNutrientEntity>>? = null,
    val nutrients: Map<String, RobotoffNutrientEntity>? = null,
    val annotation: RobotoffNutrientAnnotation? = null,
    val wasUpdated: Boolean? = null
) {

    fun getNutrientEntity(nutrient: Nutrient, perSize: PerSize, entityKey: String? = null): RobotoffNutrientEntity? {
        val tag = nutrient.getOffTagPerSize(perSize)

        if (entityKey != null) {
            entities?.get(entityKey)?.forEach { entity ->
                if (entity.entity == tag) return entity
            }
            return null
        }

        return nutrients?.get(tag)
    }

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffNutrientDataWrapper = RobotoffNutrientDataWrapper(
            entities = (json["entities"] as Map<String, Any?>?)?.mapValues { (it.value as List<*>) .map { RobotoffNutrientEntity.fromJson(it as Map<String, Any?>) } },
            nutrients = (json["nutrients"] as Map<String, Any?>?)?.mapValues { RobotoffNutrientEntity.fromJson(it.value as Map<String, Any?>) },
            annotation = json["annotation"]?.let { RobotoffNutrientAnnotation.fromJson(it as Map<String, Any?>) },
            wasUpdated = json["was_updated"] as Boolean?
        )
    }

    fun toJson(): Map<String, Any?> = mapOfNotNull(
        "entities" to entities,
        "nutrients" to nutrients,
        "annotation" to annotation,
        "was_updated" to wasUpdated
    )
}