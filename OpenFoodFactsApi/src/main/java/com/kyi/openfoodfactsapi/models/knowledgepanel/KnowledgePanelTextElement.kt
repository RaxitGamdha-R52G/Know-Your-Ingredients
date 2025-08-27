package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.JsonObject

/**
 * Description element of the Knowledge panel.
 */
class KnowledgePanelTextElement(
    val html: String,
    val type: KnowledgePanelTextElementType? = null,
    val sourceLanguage: String? = null,
    val sourceLocale: String? = null,
    val sourceText: String? = null,
    val sourceUrl: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOf(
        "html" to html,
        "type" to type?.name?.lowercase(),
        "source_language" to sourceLanguage,
        "source_lc" to sourceLocale,
        "source_text" to sourceText,
        "source_url" to sourceUrl
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): KnowledgePanelTextElement =
            KnowledgePanelTextElement(
                html = json["html"] as String,
                type = json["type"]?.let { value ->
                    KnowledgePanelTextElementType.entries.firstOrNull { it.name.lowercase() == value as String }
                        ?: KnowledgePanelTextElementType.DEFAULT
                },
                sourceLanguage = json["source_language"] as String?,
                sourceLocale = json["source_lc"] as String?,
                sourceText = json["source_text"] as String?,
                sourceUrl = json["source_url"] as String?
            )
    }
}