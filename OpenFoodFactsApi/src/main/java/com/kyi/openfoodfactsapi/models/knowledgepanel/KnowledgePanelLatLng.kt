package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Element representing a lat/long positioning of a map pointer.
 */
class KnowledgePanelLatLng(
    val lat: Double,
    val lng: Double
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "lat" to lat,
        "lng" to lng
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelLatLng = KnowledgePanelLatLng(
            lat = json["lat"] as Double,
            lng = json["lng"] as Double
        )
    }
}