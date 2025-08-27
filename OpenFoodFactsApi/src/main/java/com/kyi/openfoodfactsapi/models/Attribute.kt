package com.kyi.openfoodfactsapi.models

import com.kyi.openfoodfactsapi.sources.JsonObject

class Attribute(
    val id: String? = null,
    val name: String? = null,
    val title: String? = null,
    val iconUrl: String? = null,
    val defaultF: String? = null,
    val settingNote: String? = null,
    val settingName: String? = null,
    val description: String? = null,
    val descriptionShort: String? = null,
    val match: Double? = null,
    val status: String? = null,
    val panelId: String? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = removeNullEntries(
        mapOf(
            JSON_TAG_ID to id,
            JSON_TAG_NAME to name,
            JSON_TAG_TITLE to title,
            JSON_TAG_ICON_URL to iconUrl,
            JSON_TAG_DEFAULT to defaultF,
            JSON_TAG_SETTING_NOTE to settingNote,
            JSON_TAG_SETTING_NAME to settingName,
            JSON_TAG_DESCRIPTION to description,
            JSON_TAG_DESCRIPTION_SHORT to descriptionShort,
            JSON_TAG_MATCH to match,
            JSON_TAG_STATUS to status,
            JSON_TAG_PANEL_ID to panelId
        )
    )

    companion object {
        private const val JSON_TAG_ID = "id"
        private const val JSON_TAG_NAME = "name"
        private const val JSON_TAG_TITLE = "title"
        private const val JSON_TAG_ICON_URL = "icon_url"
        private const val JSON_TAG_DEFAULT = "default"
        private const val JSON_TAG_SETTING_NOTE = "setting_note"
        private const val JSON_TAG_SETTING_NAME = "setting_name"
        private const val JSON_TAG_DESCRIPTION = "description"
        private const val JSON_TAG_DESCRIPTION_SHORT = "description_short"
        private const val JSON_TAG_MATCH = "match"
        private const val JSON_TAG_STATUS = "status"
        private const val JSON_TAG_PANEL_ID = "panel_id"

        const val STATUS_UNKNOWN = "unknown"
        const val STATUS_KNOWN = "known"

        const val ATTRIBUTE_NUTRISCORE = "nutriscore"
        const val ATTRIBUTE_LOW_SALT = "low_salt"
        const val ATTRIBUTE_LOW_SUGARS = "low_sugars"
        const val ATTRIBUTE_LOW_FAT = "low_fat"
        const val ATTRIBUTE_LOW_SATURATED_FAT = "low_saturated_fat"

        const val ATTRIBUTE_NOVA = "nova"
        const val ATTRIBUTE_ADDITIVES = "additives"

        const val ATTRIBUTE_ALLERGENS_NO_GLUTEN = "allergens_no_gluten"
        const val ATTRIBUTE_ALLERGENS_NO_MILK = "allergens_no_milk"
        const val ATTRIBUTE_ALLERGENS_NO_EGGS = "allergens_no_eggs"
        const val ATTRIBUTE_ALLERGENS_NO_NUTS = "allergens_no_nuts"
        const val ATTRIBUTE_ALLERGENS_NO_PEANUTS = "allergens_no_peanuts"
        const val ATTRIBUTE_ALLERGENS_NO_SESAME_SEEDS = "allergens_no_sesame_seeds"
        const val ATTRIBUTE_ALLERGENS_NO_SOYBEANS = "allergens_no_soybeans"
        const val ATTRIBUTE_ALLERGENS_NO_CELERY = "allergens_no_celery"
        const val ATTRIBUTE_ALLERGENS_NO_MUSTARD = "allergens_no_mustard"
        const val ATTRIBUTE_ALLERGENS_NO_LUPIN = "allergens_no_lupin"
        const val ATTRIBUTE_ALLERGENS_NO_FISH = "allergens_no_fish"
        const val ATTRIBUTE_ALLERGENS_NO_CRUSTACEANS = "allergens_no_crustaceans"
        const val ATTRIBUTE_ALLERGENS_NO_MOLLUSCS = "allergens_no_molluscs"
        const val ATTRIBUTE_ALLERGENS_NO_SULPHUR_DIOXIDE_AND_SULPHITES = "allergens_no_sulphur_dioxide_and_sulphites"

        const val ATTRIBUTE_VEGETARIAN = "vegetarian"
        const val ATTRIBUTE_VEGAN = "vegan"
        const val ATTRIBUTE_PALM_OIL_FREE = "palm_oil_free"

        const val ATTRIBUTE_LABELS_ORGANIC = "labels_organic"
        const val ATTRIBUTE_LABELS_FAIR_TRADE = "labels_fair_trade"

        const val ATTRIBUTE_ECOSCORE = "ecoscore"
        const val ATTRIBUTE_FOREST_FOOTPRINT = "forest_footprint"

        fun fromJson(json: Map<String, Any?>): Attribute = Attribute(
            id = json[JSON_TAG_ID] as String?,
            name = json[JSON_TAG_NAME] as String?,
            title = json[JSON_TAG_TITLE] as String?,
            iconUrl = json[JSON_TAG_ICON_URL] as String?,
            defaultF = json[JSON_TAG_DEFAULT] as String?,
            settingNote = json[JSON_TAG_SETTING_NOTE] as String?,
            settingName = json[JSON_TAG_SETTING_NAME] as String?,
            description = json[JSON_TAG_DESCRIPTION] as String?,
            descriptionShort = json[JSON_TAG_DESCRIPTION_SHORT] as String?,
            match = parseDouble(json[JSON_TAG_MATCH]),
            status = json[JSON_TAG_STATUS] as String?,
            panelId = json[JSON_TAG_PANEL_ID] as String?
        )
    }

    override fun toString(): String = "Attribute(${toJson()})"
}