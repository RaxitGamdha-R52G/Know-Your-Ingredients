package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyPackagingField(override val offTag: String) : OffTagged {
    ALL("all"),
    NAME("name"),
    SYNONYMS("synonyms"),
    WIKIDATA("wikidata"),
    CHILDREN("children"),
    PARENTS("parents")
}

class TaxonomyPackaging : JsonObject() {

    var name: Map<OpenFoodFactsLanguage, String>? = null

    var synonyms: Map<OpenFoodFactsLanguage, List<String>>? = null

    var wikidata: Map<OpenFoodFactsLanguage, String>? = null

    var children: List<String>? = null

    var parents: List<String>? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyPackaging = TaxonomyPackaging().apply {
            name = LanguageHelper.fromJsonStringMap(json["name"])
            synonyms = LanguageHelper.fromJsonStringsListMap(json["synonyms"])
            wikidata = LanguageHelper.fromJsonStringMap(json["wikidata"])
            children = (json["children"] as List<*>?)?.map { it as String }
            parents = (json["parents"] as List<*>?)?.map { it as String }
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "name" to LanguageHelper.toJsonStringMap(name),
        "synonyms" to LanguageHelper.toJsonStringsListMap(synonyms),
        "wikidata" to LanguageHelper.toJsonStringMap(wikidata),
        "children" to children,
        "parents" to parents
    )

    override fun toString(): String = toJson().toString()
}