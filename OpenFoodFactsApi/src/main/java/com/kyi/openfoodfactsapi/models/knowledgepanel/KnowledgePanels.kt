package com.kyi.openfoodfactsapi.models.knowledgepanel

import kotlin.collections.iterator

class KnowledgePanels(val panelIdToPanelMap: Map<String, KnowledgePanel>) {

    companion object {

        fun fromJson(json: Map<String, Any>): KnowledgePanels {
            val map = mutableMapOf<String, KnowledgePanel>()
            for ((panelId, value) in json) {
                map[panelId] = KnowledgePanel.fromJson(value as Map<String, Any>)
            }
            return KnowledgePanels(map)
        }

        fun empty(): KnowledgePanels = KnowledgePanels(emptyMap())

        fun fromJsonHelper(json: Map<*, *>?): KnowledgePanels? =
            if (json == null) null else fromJson(json as Map<String, Any>)

        fun toJsonHelper(knowledgePanels: KnowledgePanels?): Map<String, Any>? {
            val result = mutableMapOf<String, Any>()
            if (knowledgePanels == null) {
                return null
            }
            for ((key, value) in knowledgePanels.panelIdToPanelMap) {
                result[key] = value.toJson()
            }
            return result
        }
    }

    override fun toString(): String = "KnowledgePanels(map: $panelIdToPanelMap)"
}