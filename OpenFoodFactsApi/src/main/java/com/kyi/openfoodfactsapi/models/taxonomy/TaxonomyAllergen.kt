package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyAllergenField(override val offTag: String) : OffTagged {
    ALL("all"),
    NAME("name"),
    SYNONYMS("synonyms"),
    WIKIDATA("wikidata")
}

class TaxonomyAllergen(
    val name: Map<OpenFoodFactsLanguage, String>? = null,
    val synonyms: Map<OpenFoodFactsLanguage, List<String>>? = null,
    val wikidata: Map<OpenFoodFactsLanguage, String>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "name" to LanguageHelper.toJsonStringMap(name),
        "synonyms" to LanguageHelper.toJsonStringsListMap(synonyms),
        "wikidata" to LanguageHelper.toJsonStringMap(wikidata)
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyAllergen = TaxonomyAllergen(
            name = LanguageHelper.fromJsonStringMap(json["name"]),
            synonyms = LanguageHelper.fromJsonStringsListMap(json["synonyms"]),
            wikidata = LanguageHelper.fromJsonStringMap(json["wikidata"])
        )
    }

    override fun toString(): String = toJson().toString()
}