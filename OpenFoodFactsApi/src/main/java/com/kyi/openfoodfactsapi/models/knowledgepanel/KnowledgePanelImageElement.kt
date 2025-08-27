package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Image that represents the KnowledgePanel.
 */
class KnowledgePanelImageElement(
    val url: String,
    val width: Int? = null,
    val height: Int? = null,
    val altText: String? = null,
    val linkUrl: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "url" to url,
        "width" to width,
        "height" to height,
        "alt" to altText,
        "link_url" to linkUrl
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelImageElement =
            KnowledgePanelImageElement(
                url = json["url"] as String,
                width = json["width"] as Int?,
                height = json["height"] as Int?,
                altText = json["alt"] as String?,
                linkUrl = json["link_url"] as String?
            )
    }
}