package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class BadgeBase(
    val badgeName: String,
    val level: Int,
    val userId: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "user_id" to userId,
        "badge_name" to badgeName,
        "level" to level
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): BadgeBase = BadgeBase(
            badgeName = json["badge_name"] as String,
            level = (json["level"] as Number).toInt(),
            userId = json["user_id"] as String?
        )
    }

    override fun toString(): String = "BadgeBase(badgeName: $badgeName" +
            ", level: $level" +
            "${if (userId == null) "" else ", userId: $userId"}" +
            ")"
}