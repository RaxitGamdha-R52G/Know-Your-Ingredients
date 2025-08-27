package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyCountryField(override val offTag: String) : OffTagged {
    ALL("all"),
    COUNTRY_CODE_2("country_code_2"),
    COUNTRY_CODE_3("country_code_3"),
    LANGUAGES("languages"),
    NAME("name"),
    OFFICIAL_COUNTRY_CODE_2("official_country_code_2"),
    SYNONYMS("synonyms"),
    WIKIDATA("wikidata")
}

class TaxonomyCountry : JsonObject() {

    var countryCode2: String? = null

    var countryCode3: String? = null

    var languages: List<OpenFoodFactsLanguage>? = null

    var name: Map<OpenFoodFactsLanguage, String>? = null

    var synonyms: Map<OpenFoodFactsLanguage, List<String>>? = null

    var wikidata: String? = null

    var officialCountryCode2: String? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyCountry = TaxonomyCountry().apply {
            countryCode2 = LanguageHelper.fromJsonStringMapIsoUnique(json["country_code_2"])
            countryCode3 = LanguageHelper.fromJsonStringMapIsoUnique(json["country_code_3"])
            languages = LanguageHelper.fromJsonStringMapIsoList(json["language_codes"])
            name = LanguageHelper.fromJsonStringMap(json["name"])
            synonyms = LanguageHelper.fromJsonStringMapList(json["synonyms"])
            wikidata = LanguageHelper.fromJsonStringMapIsoUnique(json["wikidata"])
            officialCountryCode2 = LanguageHelper.fromJsonStringMapIsoUnique(json["official_country_code_2"])
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "country_code_2" to countryCode2,
        "country_code_3" to countryCode3,
        "language_codes" to languages?.map { it.offTag },
        "name" to name,
        "synonyms" to synonyms,
        "wikidata" to wikidata,
        "official_country_code_2" to officialCountryCode2
    )

    override fun toString(): String = toJson().toString()
}