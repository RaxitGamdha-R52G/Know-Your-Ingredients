package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Element representing a geo location of a map pointer.
 */
class KnowledgePanelGeoPointer(
    val geo: KnowledgePanelLatLng? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "geo" to geo?.toJson()
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelGeoPointer = KnowledgePanelGeoPointer(
            geo = json["geo"]?.let { KnowledgePanelLatLng.fromJson(it as Map<String, Any?>) }
        )
    }
}