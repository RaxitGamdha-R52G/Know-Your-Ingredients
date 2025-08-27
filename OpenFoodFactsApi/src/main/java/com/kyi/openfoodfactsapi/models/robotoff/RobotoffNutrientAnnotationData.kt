package com.kyi.openfoodfactsapi.models.robotoff

import UnitHelper
import com.kyi.openfoodfactsapi.models.NutrientModifier

class RobotoffNutrientAnnotationData(
    var unit: Unit? = null,
    var valueWithModifer: String
) {

    val modifier: NutrientModifier? get() = NutrientModifier.Companion.fromValue(valueWithModifer)

    val value: Double? get() {
        val trimmed = valueWithModifer.trim()
        if (trimmed.isEmpty()) return null
        return if (modifier == null) trimmed.toDoubleOrNull() else trimmed.substring(1).toDoubleOrNull()
    }

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffNutrientAnnotationData = RobotoffNutrientAnnotationData(
            unit = UnitHelper.stringToUnit(json["unit"] as String?),
            valueWithModifer = json["value"] as String
        )
    }

    fun toJson(): Map<String, Any?> = mapOf(
        "unit" to UnitHelper.unitToString(unit),
        "value" to valueWithModifer
    )
}
