package com.kyi.openfoodfactsapi.search

import com.kyi.openfoodfactsapi.sources.JsonObject

class AutocompleteSearchResult(
    val took: Int? = null,
    val timedOut: Boolean? = null,
    val options: List<AutocompleteSingleResult>? = null
) : JsonObject() {

    companion object {

        fun fromJson(json: Map<String, Any?>): AutocompleteSearchResult = AutocompleteSearchResult(
            took = JsonObject.parseInt(json["took"]),
            timedOut = JsonObject.parseBool(json["timed_out"]),
            options = (json["options"] as List<*>?)?.map { AutocompleteSingleResult.fromJson(it as Map<String, Any?>) }
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "took" to took,
        "timed_out" to timedOut,
        "options" to options
    )
}