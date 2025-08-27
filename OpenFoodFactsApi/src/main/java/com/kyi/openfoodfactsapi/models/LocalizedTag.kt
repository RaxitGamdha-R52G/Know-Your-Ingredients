package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class LocalizedTag : JsonObject() {

    var id: String? = null

    var name: String? = null

    var lcName: String? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): LocalizedTag = LocalizedTag().apply {
            id = json["id"] as String?
            name = json["name"] as String?
            lcName = json["lc_name"] as String?
        }

        fun objToJson(tag: LocalizedTag?): Map<String, Any> =
            tag?.toJson() ?: emptyMap()
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "id" to id,
        "name" to name,
        "lc_name" to lcName
    )

    fun toServerData(): Map<String, String> = toDataStatic(toJson())

    override fun toString(): String = toServerData().toString()
}