package com.kyi.openfoodfactsapi.models.robotoff

class RobotoffNutrientEntity(
    val start: Int? = null,
    val end: Int? = null,
    val text: String? = null,
    val unit: Unit? = null,
    val score: Double? = null,
    val valid: Boolean? = null,
    val value: String? = null,
    val entity: String? = null,
    val charStart: Int? = null,
    val charEnd: Int? = null
) {

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffNutrientEntity = RobotoffNutrientEntity(
            start = (json["start"] as Number?)?.toInt(),
            end = (json["end"] as Number?)?.toInt(),
            text = json["text"] as String?,
            unit = UnitHelper.stringToUnit(json["unit"] as String?),
            score = (json["score"] as Number?)?.toDouble(),
            valid = json["valid"] as Boolean?,
            value = json["value"] as String?,
            entity = json["entity"] as String?,
            charStart = (json["char_start"] as Number?)?.toInt(),
            charEnd = (json["char_end"] as Number?)?.toInt()
        )
    }

    fun toJson(): Map<String, Any?> = mapOfNotNull(
        "start" to start,
        "end" to end,
        "text" to text,
        "unit" to UnitHelper.unitToString(unit),
        "score" to score,
        "valid" to valid,
        "value" to value,
        "entity" to entity,
        "char_start" to charStart,
        "char_end" to charEnd
    )
}