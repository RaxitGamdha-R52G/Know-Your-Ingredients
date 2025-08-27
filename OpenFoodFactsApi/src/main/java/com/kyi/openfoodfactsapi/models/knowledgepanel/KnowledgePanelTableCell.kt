package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Provides the values for each table cell inside a KnowledgePanel table.
 */
class KnowledgePanelTableCell(
    val text: String,
    val percent: Double? = null,
    val iconUrl: String? = null,
    val evaluation: Evaluation? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "text" to text,
        "percent" to percent,
        "icon_url" to iconUrl,
        "evaluation" to evaluation?.name?.lowercase()
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelTableCell = KnowledgePanelTableCell(
            text = json["text"] as String,
            percent = json["percent"] as Double?,
            iconUrl = json["icon_url"] as String?,
            evaluation = json["evaluation"]?.let { value ->
                Evaluation.entries.firstOrNull { it.name.lowercase() == value as String }
                    ?: Evaluation.UNKNOWN
            }
        )
    }
}
