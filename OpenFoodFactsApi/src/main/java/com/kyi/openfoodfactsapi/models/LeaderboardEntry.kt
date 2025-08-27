package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class LeaderboardEntry(
    val score: Int,
    val userId: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "user_id" to userId,
        "score" to score
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): LeaderboardEntry = LeaderboardEntry(
            score = (json["score"] as Number).toInt(),
            userId = json["user_id"] as String?
        )
    }

    override fun toString(): String = "LeaderboardEntry(score: $score" +
            "${if (userId == null) ", no user id" else ", userId: $userId"}" +
            ")"
}