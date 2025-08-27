package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Folksonomy: statistics around a tag key.
 */
class KeyStats(
    val key: String,
    val count: Int,
    val values: Int
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "k" to key,
        "count" to count,
        "values" to values
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KeyStats = KeyStats(
            key = json["k"] as String,
            count = (json["count"] as Number).toInt(),
            values = (json["values"] as Number).toInt()
        )
    }

    override fun toString(): String = toJson().toString()
}