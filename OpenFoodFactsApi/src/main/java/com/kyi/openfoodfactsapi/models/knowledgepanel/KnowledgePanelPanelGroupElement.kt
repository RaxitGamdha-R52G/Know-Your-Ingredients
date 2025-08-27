package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Element representing a Panel group that contains 1+ KnowledgePanels.
 */
class KnowledgePanelPanelGroupElement(
    val title: String? = null,
    val panelIds: List<String>
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "title" to title,
        "panel_ids" to panelIds
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelPanelGroupElement =
            KnowledgePanelPanelGroupElement(
                title = json["title"] as String?,
                panelIds = json["panel_ids"] as List<String>
            )
    }
}