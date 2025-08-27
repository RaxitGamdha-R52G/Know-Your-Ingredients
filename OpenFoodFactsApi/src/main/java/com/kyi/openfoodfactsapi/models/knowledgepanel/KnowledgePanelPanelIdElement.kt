package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Element representing a Panel Id of a KnowledgePanel. This element is a
 * Knowledge panel itself, the KnowledgePanel can be found in the list of
 * Knowledge panels using the id.
 */
class KnowledgePanelPanelIdElement(
    val panelId: String
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "panel_id" to panelId
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelPanelIdElement =
            KnowledgePanelPanelIdElement(
                panelId = json["panel_id"] as String
            )
    }
}