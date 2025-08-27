package com.kyi.openfoodfactsapi.models.robotoff

import com.kyi.openfoodfactsapi.sources.JsonObject

class RobotoffQuestionResult(
    val status: String? = null,
    val questions: List<RobotoffQuestion>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "status" to status,
        "questions" to questions
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): RobotoffQuestionResult = RobotoffQuestionResult(
            status = json["status"] as String?,
            questions = (json["questions"] as List<*>?)?.map { RobotoffQuestion.fromJson(it as Map<String, Any?>) }
        )
    }
}