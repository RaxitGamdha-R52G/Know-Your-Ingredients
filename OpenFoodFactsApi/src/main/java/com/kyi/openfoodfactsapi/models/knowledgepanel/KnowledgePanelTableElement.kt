package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Element representing a tabular data for the KnowledgePanel.
 */
class KnowledgePanelTableElement(
    val id: String,
    val title: String? = null,
    val columns: List<KnowledgePanelTableColumn>,
    val rows: List<KnowledgePanelTableRowElement>
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "id" to id,
        "title" to title,
        "columns" to columns.map { it.toJson() },
        "rows" to rows.map { it.toJson() }
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelTableElement =
            KnowledgePanelTableElement(
                id = json["id"] as String,
                title = json["title"] as String?,
                columns = (json["columns"] as List<*>).map { KnowledgePanelTableColumn.fromJson(it as Map<String, Any?>) },
                rows = (json["rows"] as List<*>).map { KnowledgePanelTableRowElement.fromJson(it as Map<String, Any?>) }
            )
    }
}
