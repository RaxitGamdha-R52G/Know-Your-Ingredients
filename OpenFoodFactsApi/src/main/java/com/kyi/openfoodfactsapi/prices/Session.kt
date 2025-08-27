package com.kyi.openfoodfactsapi.prices

import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.utils.JsonHelper

class Session(
    var userId: String,
    var created: DateTime,
    var lastUsed: DateTime? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): Session = Session(
            userId = json["user_id"] as String,
            created = JsonHelper.stringTimestampToDate(json["created"] as String),
            lastUsed = JsonHelper.nullableStringTimestampToDate(json["last_used"] as String?)
        )

        const val INVALID_AUTH_STATUS_CODE = 401

        const val INVALID_AUTH_MESSAGE = "Invalid authentication credentials"

        const val INVALID_ACTION_WITH_AUTH_STATUS_CODE = 403

        const val INVALID_ACTION_WITH_AUTH_MESSAGE = "Authentication credentials were not provided."
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "user_id" to userId,
        "created" to created.toIso8601String(),
        "last_used" to lastUsed?.toIso8601String()
    )
}