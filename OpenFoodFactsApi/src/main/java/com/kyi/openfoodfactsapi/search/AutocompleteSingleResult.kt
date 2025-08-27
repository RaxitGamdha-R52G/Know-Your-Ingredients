package com.kyi.openfoodfactsapi.search

import com.kyi.openfoodfactsapi.sources.JsonObject

class AutocompleteSingleResult(
    val id: String,
    val text: String,
    val taxonomyNameAsString: String
) : JsonObject() {

    val taxonomyName: TaxonomyName? get() = TaxonomyName.fromOffTag(taxonomyNameAsString)

    companion object {

        fun fromJson(json: Map<String, Any?>): AutocompleteSingleResult = AutocompleteSingleResult(
            id = json["id"] as String,
            text = json["text"] as String,
            taxonomyNameAsString = json["taxonomy_name"] as String
        )
    }

    override fun toJson(): Map<String, Any?> = mapOf(
        "id" to id,
        "text" to text,
        "taxonomy_name" to taxonomyNameAsString
    )
}