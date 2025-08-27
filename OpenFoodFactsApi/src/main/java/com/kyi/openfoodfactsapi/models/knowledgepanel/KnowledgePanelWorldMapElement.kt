package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Element representing a world map.
 */
class KnowledgePanelWorldMapElement(
    val pointers: List<KnowledgePanelGeoPointer>
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "pointers" to pointers.map { it.toJson() }
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelWorldMapElement =
            KnowledgePanelWorldMapElement(
                pointers = (json["pointers"] as List<*>).map { KnowledgePanelGeoPointer.fromJson(it as Map<String, Any?>) }
            )
    }
}