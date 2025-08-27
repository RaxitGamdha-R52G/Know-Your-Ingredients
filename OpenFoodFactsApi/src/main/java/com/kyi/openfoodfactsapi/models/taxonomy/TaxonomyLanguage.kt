package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyLanguageField(override val offTag: String) : OffTagged {
    ALL("all"),
    LANGUAGE_CODE_2("language_code_2"),
    LANGUAGE_CODE_3("language_code_3"),
    NAME("name"),
    WIKIDATA("wikidata")
}

class TaxonomyLanguage : JsonObject() {

    var languageCode2: Map<OpenFoodFactsLanguage, String>? = null

    var languageCode3: Map<OpenFoodFactsLanguage, String>? = null

    var name: Map<OpenFoodFactsLanguage, String>? = null

    var wikidata: Map<OpenFoodFactsLanguage, String>? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyLanguage = TaxonomyLanguage().apply {
            languageCode2 = LanguageHelper.fromJsonStringMap(json["language_code_2"])
            languageCode3 = LanguageHelper.fromJsonStringMap(json["language_code_3"])
            name = LanguageHelper.fromJsonStringMap(json["name"])
            wikidata = LanguageHelper.fromJsonStringMap(json["wikidata"])
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "language_code_2" to LanguageHelper.toJsonStringMap(languageCode2),
        "language_code_3" to LanguageHelper.toJsonStringMap(languageCode3),
        "name" to LanguageHelper.toJsonStringMap(name),
        "wikidata" to LanguageHelper.toJsonStringMap(wikidata)
    )

    override fun toString(): String = toJson().toString()
}