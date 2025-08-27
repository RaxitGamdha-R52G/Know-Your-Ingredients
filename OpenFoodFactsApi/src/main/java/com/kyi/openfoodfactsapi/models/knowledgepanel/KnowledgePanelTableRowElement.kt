package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * A table row inside Table element of KonwledgePanel
 */
class KnowledgePanelTableRowElement(
    val values: List<KnowledgePanelTableCell>
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "values" to values.map { it.toJson() }
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelTableRowElement =
            KnowledgePanelTableRowElement(
                values = (json["values"] as List<*>).map { KnowledgePanelTableCell.fromJson(it as Map<String, Any?>) }
            )
    }
}
