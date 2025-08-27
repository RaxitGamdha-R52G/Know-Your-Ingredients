package com.kyi.openfoodfactsapi.utils

import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TagType(override val offTag: String) : OffTagged {
    STATES("states"),
    LANGUAGES("languages"),
    LABELS("labels"),
    CATEGORIES("categories"),
    COUNTRIES("countries"),
    INGREDIENTS("ingredients"),
    TRACES("traces"),
    ADDITIVES("additives"),
    ALLERGENS("allergens"),
    PACKAGING("packaging"),
    ORIGINS("origins"),
    PACKAGING_SHAPES("packaging_shapes"),
    PACKAGING_MATERIALS("packaging_materials"),
    PACKAGING_RECYCLING("packaging_recycling"),
    NOVA("nova_groups"),
    EMB_CODES("emb_codes");

    companion object {

        fun fromOffTag(offTag: String?): TagType? = entries.firstOrNull { it.offTag == offTag }
    }
}