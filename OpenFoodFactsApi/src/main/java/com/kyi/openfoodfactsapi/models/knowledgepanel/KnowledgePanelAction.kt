package com.kyi.openfoodfactsapi.models.knowledgepanel

import com.kyi.openfoodfactsapi.sources.OffTagged

/**
 * Possible needed contribute actions.
 *
 * cf. [KnowledgePanelActionElement.actions].
 */
enum class KnowledgePanelAction(override val offTag: String) : OffTagged {
    ADD_CATEGORIES("add_categories"),
    ADD_INGREDIENTS_TEXT("add_ingredients_text"),
    ADD_INGREDIENTS_IMAGE("add_ingredients_image"),
    ADD_PACKAGING_IMAGE("add_packaging_image"),
    ADD_PACKAGING_TEXT("add_packaging_text"),
    ADD_NUTRITION_FACTS("add_nutrition_facts"),
    ADD_ORIGINS("add_origins"),
    ADD_STORES("add_stores"),
    ADD_LABELS("add_labels"),
    ADD_COUNTRIES("add_countries");

    companion object {
        fun fromOffTag(offTag: String?): KnowledgePanelAction? =
            entries.firstOrNull { it.offTag == offTag }
    }
}