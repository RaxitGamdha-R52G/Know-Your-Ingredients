package com.kyi.openfoodfactsapi.models.parameter

import OffTagged
import com.kyi.openfoodfactsapi.sources.Parameter

/**
 * Filter types for advanced search parameters
 */
enum class TagFilterType(override val offTag: String) : OffTagged {
    BRANDS("brands"),
    CATEGORIES("categories"),
    PACKAGING("packaging"),
    LABELS("labels"),
    ORIGINS("origins"),
    MANUFACTURING_PLACES("manufacturing_places"),
    EMB_CODES("emb_codes"),
    PURCHASE_PLACES("purchase_places"),
    STORES("stores"),
    COUNTRIES("countries"),
    ADDITIVES("additives"),
    ALLERGENS("allergens"),
    TRACES("traces"),
    NUTRITION_GRADES("nutrition_grades"),
    STATES("states"),
    INGREDIENTS("ingredients"),
    NOVA_GROUPS("nova_groups"),
    LANGUAGES("languages"),
    CREATOR("creator"),
    EDITORS("editors"),
    PHOTOGRAPHERS("photographers"),
    INFORMERS("informers"),
    LANG("lang");
}

/**
 * Tag filter ("LIST contains/without ITEM") search API parameter
 *
 * Eg. 'nutrition_grades' contains 'A'
 */
class TagFilter private constructor(
    val tagType: String,
    val contains: Boolean,
    val tagName: String
) : Parameter() {

    // actually not used
    override fun getName(): String = ""

    // actually not used
    override fun getValue(): String = ""

    fun getTagType(): String = tagType

    fun getContains(): String = if (contains) "contains" else "without"

    fun getTagName(): String = tagName

    constructor(
        tagFilterType: TagFilterType,
        tagName: String,
        contains: Boolean = true
    ) : this(
        tagType = tagFilterType.offTag,
        contains = contains,
        tagName = tagName
    )
}