package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * A descriptor that describes the type and label of each column.
 */
class KnowledgePanelTableColumn(
    val text: String,
    val textForSmallScreens: String? = null,
    val showByDefault: Boolean? = null,
    val columnGroupId: String? = null,
    val style: String? = null,
    val type: KnowledgePanelColumnType? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "text" to text,
        "text_for_small_screens" to textForSmallScreens,
        "shown_by_default" to showByDefault,
        "column_group_id" to columnGroupId,
        "style" to style,
        "type" to type?.name?.lowercase()
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelTableColumn =
            KnowledgePanelTableColumn(
                text = json["text"] as String,
                textForSmallScreens = json["text_for_small_screens"] as String?,
                showByDefault = json["shown_by_default"] as Boolean?,
                columnGroupId = json["column_group_id"] as String?,
                style = json["style"] as String?,
                type = json["type"]?.let { value ->
                    KnowledgePanelColumnType.entries.firstOrNull { it.name.lowercase() == value as String }
                        ?: KnowledgePanelColumnType.TEXT
                }
            )
    }
}
