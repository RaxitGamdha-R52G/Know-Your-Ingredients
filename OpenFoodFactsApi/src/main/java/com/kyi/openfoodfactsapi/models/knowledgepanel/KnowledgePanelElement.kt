package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * KnowledgePanelElement is a single unit of KnowledgePanel that can be rendered on the client.
 *
 * An Element could be one of [{@code ]KnowledgePanelElementType].
 */
class KnowledgePanelElement(
    val elementType: KnowledgePanelElementType,
    val textElement: KnowledgePanelTextElement? = null,
    val imageElement: KnowledgePanelImageElement? = null,
    val panelElement: KnowledgePanelPanelIdElement? = null,
    val panelGroupElement: KnowledgePanelPanelGroupElement? = null,
    val tableElement: KnowledgePanelTableElement? = null,
    val mapElement: KnowledgePanelWorldMapElement? = null,
    val actionElement: KnowledgePanelActionElement? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "element_type" to elementType.name.lowercase(),
        "text_element" to textElement?.toJson(),
        "image_element" to imageElement?.toJson(),
        "panel_element" to panelElement?.toJson(),
        "panel_group_element" to panelGroupElement?.toJson(),
        "table_element" to tableElement?.toJson(),
        "map_element" to mapElement?.toJson(),
        "action_element" to actionElement?.toJson()
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelElement = KnowledgePanelElement(
            elementType = json["element_type"]?.let { value ->
                KnowledgePanelElementType.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: KnowledgePanelElementType.UNKNOWN
            } ?: KnowledgePanelElementType.UNKNOWN,
            textElement = json["text_element"]?.let { KnowledgePanelTextElement.fromJson(it as Map<String, Any?>) },
            imageElement = json["image_element"]?.let { KnowledgePanelImageElement.fromJson(it as Map<String, Any?>) },
            panelElement = json["panel_element"]?.let { KnowledgePanelPanelIdElement.fromJson(it as Map<String, Any?>) },
            panelGroupElement = json["panel_group_element"]?.let {
                KnowledgePanelPanelGroupElement.fromJson(
                    it as Map<String, Any?>
                )
            },
            tableElement = json["table_element"]?.let { KnowledgePanelTableElement.fromJson(it as Map<String, Any?>) },
            mapElement = json["map_element"]?.let { KnowledgePanelWorldMapElement.fromJson(it as Map<String, Any?>) },
            actionElement = json["action_element"]?.let { KnowledgePanelActionElement.fromJson(it as Map<String, Any?>) }
        )
    }
}