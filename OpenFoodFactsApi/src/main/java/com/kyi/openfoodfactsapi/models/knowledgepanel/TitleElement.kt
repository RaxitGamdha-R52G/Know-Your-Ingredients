package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * An element representing the title of the KnowledgePanel which could consist
 * of a text title, subtitle and an icon.
 */
class TitleElement(
    val title: String,
    val name: String? = null,
    val subtitle: String? = null,
    val grade: Grade? = null,
    val type: TitleElementType? = null,
    val iconUrl: String? = null,
    val iconColorFromEvaluation: Boolean? = false
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "title" to title,
        "name" to name,
        "subtitle" to subtitle,
        "grade" to grade?.name?.lowercase(),
        "type" to type?.name?.lowercase(),
        "icon_url" to iconUrl,
        "icon_color_from_evaluation" to iconColorFromEvaluation
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): TitleElement = TitleElement(
            title = json["title"] as String,
            name = json["name"] as String?,
            subtitle = json["subtitle"] as String?,
            grade = json["grade"]?.let { value ->
                Grade.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: Grade.UNKNOWN
            },
            type = json["type"]?.let { value ->
                TitleElementType.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: TitleElementType.UNKNOWN
            },
            iconUrl = json["icon_url"] as String?,
            iconColorFromEvaluation = json["icon_color_from_evaluation"] as Boolean? ?: false
        )
    }
}