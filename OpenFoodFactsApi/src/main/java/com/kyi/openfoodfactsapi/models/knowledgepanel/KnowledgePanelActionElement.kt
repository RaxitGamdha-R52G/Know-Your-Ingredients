package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * "Contribute action" element of the Knowledge panel.
 */
class KnowledgePanelActionElement(
    val html: String? = null,
    val actions: List<String>
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "html" to html,
        "actions" to actions
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelActionElement =
            KnowledgePanelActionElement(
                html = json["html"] as String?,
                actions = json["actions"] as List<String>
            )
    }
}