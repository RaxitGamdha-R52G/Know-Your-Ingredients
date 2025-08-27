package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyNovaField(override val offTag: String) : OffTagged {
    ALL("all"),
    NAME("name"),
    SYNONYMS("synonyms")
}

class TaxonomyNova : JsonObject() {

    var name: Map<OpenFoodFactsLanguage, String>? = null

    var synonyms: Map<OpenFoodFactsLanguage, List<String>>? = null

    companion object {

        val OFF_TAGS = mapOf(
            1 to "en:1-unprocessed-or-minimally-processed-foods",
            2 to "en:2-processed-culinary-ingredients",
            3 to "en:3-processed-foods",
            4 to "en:4-ultra-processed-food-and-drink-products"
        )

        fun fromJson(json: Map<String, Any?>): TaxonomyNova = TaxonomyNova().apply {
            name = LanguageHelper.fromJsonStringMap(json["name"])
            synonyms = LanguageHelper.fromJsonStringMapList(json["synonyms"])
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "name" to LanguageHelper.toJsonStringMap(name),
        "synonyms" to LanguageHelper.toJsonStringsListMap(synonyms)
    )

    override fun toString(): String = toJson().toString()
}