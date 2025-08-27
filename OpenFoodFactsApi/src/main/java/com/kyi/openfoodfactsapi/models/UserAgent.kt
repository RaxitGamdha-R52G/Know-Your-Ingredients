package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject


class UserAgent(
    val name: String,
    val version: String? = null,
    val system: String? = null,
    val url: String? = null,
    val comment: String? = null
) : JsonObject() {

    init {
        if (name.trim().isEmpty()) throw Exception("A non empty name is required")
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "name" to name,
        "version" to version,
        "system" to system,
        "url" to url,
        "comment" to comment
    )
}