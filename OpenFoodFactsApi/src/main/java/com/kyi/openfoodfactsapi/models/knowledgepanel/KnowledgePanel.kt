package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * KnowledgePanels are a standardized and generic units of information that
 * the client can display on the product page.
 *
 * See http://shorturl.at/oxRS9 for details.
 * // NOTE: This is WIP, do not use and expect changes.
 */
class KnowledgePanel(
    val titleElement: TitleElement? = null,
    val level: Level? = null,
    val expanded: Boolean? = null,
    val elements: List<KnowledgePanelElement>? = null,
    val topics: List<String>? = null,
    val evaluation: Evaluation? = null,
    val size: KnowledgePanelSize? = null,
    val halfWidthOnMobile: Boolean? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "title_element" to titleElement?.toJson(),
        "level" to level?.name?.lowercase(),
        "expanded" to expanded,
        "elements" to elements?.map { it.toJson() },
        "topics" to topics,
        "evaluation" to evaluation?.name?.lowercase(),
        "size" to size?.name?.lowercase(),
        "half_width_on_mobile" to halfWidthOnMobile
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanel = KnowledgePanel(
            titleElement = json["title_element"]?.let { TitleElement.fromJson(it as Map<String, Any?>) },
            level = json["level"]?.let { value ->
                Level.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: Level.UNKNOWN
            },
            expanded = json["expanded"] as Boolean?,
            elements = (json["elements"] as List<*>?)
                ?.map { KnowledgePanelElement.fromJson(it as Map<String, Any?>) },
            topics = (json["topics"] as List<*>?)?.map { it as String },
            evaluation = json["evaluation"]?.let { value ->
                Evaluation.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: Evaluation.UNKNOWN
            },
            size = json["size"]?.let { value ->
                KnowledgePanelSize.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: KnowledgePanelSize.UNKNOWN
            },
            halfWidthOnMobile = json["half_width_on_mobile"] as Boolean?
        )
    }
}

