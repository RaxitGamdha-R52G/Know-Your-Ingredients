package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyCategoryField(override val offTag: String) : OffTagged {
    ALL("all"),
    AGRIBALYSE_FOOD_CODE("agribalyse_food_code"),
    AGRIBALYSE_FOOD_NAME("agribalyse_food_name"),
    AGRIBALYSE_PROXY_FOOD_CODE("agribalyse_proxy_food_code"),
    AGRIBALYSE_PROXY_FOOD_NAME("agribalyse_proxy_food_name"),
    AGRIBALYSE_PROXY_NAME("agribalyse_proxy_name"),
    CARBON_FOOTPRINT_FR_FOODGES_INGREDIENT("carbon_footprint_fr_foodges_ingredient"),
    CHILDREN("children"),
    CIQUAL_FOOD_CODE("ciqual_food_code"),
    CIQUAL_FOOD_NAME("ciqual_food_name"),
    CIQUAL_PROXY_FOOD_CODE("ciqual_proxy_food_code"),
    CIQUAL_PROXY_FOOD_NAME("ciqual_proxy_food_name"),
    COUNTRY("country"),
    GRAPEVARIETY("grapevariety"),
    INSTANCEOF("instanceof"),
    NAME("name"),
    NOVA("nova"),
    OQALI_FAMILY("oqali_family"),
    ORIGINS("origins"),
    PARENTS("parents"),
    PNNS_GROUP_1("pnns_group_1"),
    PNNS_GROUP_2("pnns_group_2"),
    PROTECTED_NAME_FILE_NUMBER("protected_name_file_number"),
    PROTECTED_NAME_TYPE("protected_name_type"),
    REGION("region"),
    SEASON_IN_COUNTRY_FR("season_in_country_fr"),
    WHO_ID("who_id"),
    WIKIDATA("wikidata"),
    WIKIDATA_CATEGORY("wikidata_category"),
    WIKIDATA_WIKIPEDIA_CATEGORY("wikidata_wikipedia_category")
}

class TaxonomyCategory(
    val agribalyseFoodCode: Map<OpenFoodFactsLanguage, String>? = null,
    val agribalyseFoodName: Map<OpenFoodFactsLanguage, String>? = null,
    val agribalyseProxyFoodCode: Map<OpenFoodFactsLanguage, String>? = null,
    val agribalyseProxyFoodName: Map<OpenFoodFactsLanguage, String>? = null,
    val agribalyseProxyName: Map<OpenFoodFactsLanguage, String>? = null,
    val carbonFootprintFrFoodgesIngredient: Map<OpenFoodFactsLanguage, String>? = null,
    val children: List<String>? = null,
    val ciqualFoodCode: Map<OpenFoodFactsLanguage, String>? = null,
    val ciqualFoodName: Map<OpenFoodFactsLanguage, String>? = null,
    val ciqualProxyFoodCode: Map<OpenFoodFactsLanguage, String>? = null,
    val ciqualProxyFoodName: Map<OpenFoodFactsLanguage, String>? = null,
    val country: Map<OpenFoodFactsLanguage, String>? = null,
    val grapevariety: Map<OpenFoodFactsLanguage, String>? = null,
    val instanceof: Map<OpenFoodFactsLanguage, String>? = null,
    val name: Map<OpenFoodFactsLanguage, String>? = null,
    val nova: Map<OpenFoodFactsLanguage, String>? = null,
    val oqaliFamily: Map<OpenFoodFactsLanguage, String>? = null,
    val origins: Map<OpenFoodFactsLanguage, String>? = null,
    val parents: List<String>? = null,
    val pnnsGroup1: Map<OpenFoodFactsLanguage, String>? = null,
    val pnnsGroup2: Map<OpenFoodFactsLanguage, String>? = null,
    val protectedNameFileNumber: Map<OpenFoodFactsLanguage, String>? = null,
    val protectedNameType: Map<OpenFoodFactsLanguage, String>? = null,
    val region: Map<OpenFoodFactsLanguage, String>? = null,
    val seasonInCountryFr: Map<OpenFoodFactsLanguage, String>? = null,
    val whoId: Map<OpenFoodFactsLanguage, String>? = null,
    val wikidata: Map<OpenFoodFactsLanguage, String>? = null,
    val wikidataCategory: Map<OpenFoodFactsLanguage, String>? = null,
    val wikidataWikipediaCategory: Map<OpenFoodFactsLanguage, String>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "agribalyse_food_code" to LanguageHelper.toJsonStringMap(agribalyseFoodCode),
        "agribalyse_food_name" to LanguageHelper.toJsonStringMap(agribalyseFoodName),
        "agribalyse_proxy_food_code" to LanguageHelper.toJsonStringMap(agribalyseProxyFoodCode),
        "agribalyse_proxy_food_name" to LanguageHelper.toJsonStringMap(agribalyseProxyFoodName),
        "agribalyse_proxy_name" to LanguageHelper.toJsonStringMap(agribalyseProxyName),
        "carbon_footprint_fr_foodges_ingredient" to LanguageHelper.toJsonStringMap(carbonFootprintFrFoodgesIngredient),
        "children" to children,
        "ciqual_food_code" to LanguageHelper.toJsonStringMap(ciqualFoodCode),
        "ciqual_food_name" to LanguageHelper.toJsonStringMap(ciqualFoodName),
        "ciqual_proxy_food_code" to LanguageHelper.toJsonStringMap(ciqualProxyFoodCode),
        "ciqual_proxy_food_name" to LanguageHelper.toJsonStringMap(ciqualProxyFoodName),
        "country" to LanguageHelper.toJsonStringMap(country),
        "grapevariety" to LanguageHelper.toJsonStringMap(grapevariety),
        "instanceof" to LanguageHelper.toJsonStringMap(instanceof),
        "name" to LanguageHelper.toJsonStringMap(name),
        "nova" to LanguageHelper.toJsonStringMap(nova),
        "oqali_family" to LanguageHelper.toJsonStringMap(oqaliFamily),
        "origins" to LanguageHelper.toJsonStringMap(origins),
        "parents" to parents,
        "pnns_group_1" to LanguageHelper.toJsonStringMap(pnnsGroup1),
        "pnns_group_2" to LanguageHelper.toJsonStringMap(pnnsGroup2),
        "protected_name_file_number" to LanguageHelper.toJsonStringMap(protectedNameFileNumber),
        "protected_name_type" to LanguageHelper.toJsonStringMap(protectedNameType),
        "region" to LanguageHelper.toJsonStringMap(region),
        "season_in_country_fr" to LanguageHelper.toJsonStringMap(seasonInCountryFr),
        "who_id" to LanguageHelper.toJsonStringMap(whoId),
        "wikidata" to LanguageHelper.toJsonStringMap(wikidata),
        "wikidata_category" to LanguageHelper.toJsonStringMap(wikidataCategory),
        "wikidata_wikipedia_category" to LanguageHelper.toJsonStringMap(wikidataWikipediaCategory)
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyCategory = TaxonomyCategory(
            agribalyseFoodCode = LanguageHelper.fromJsonStringMap(json["agribalyse_food_code"]),
            agribalyseFoodName = LanguageHelper.fromJsonStringMap(json["agribalyse_food_name"]),
            agribalyseProxyFoodCode = LanguageHelper.fromJsonStringMap(json["agribalyse_proxy_food_code"]),
            agribalyseProxyFoodName = LanguageHelper.fromJsonStringMap(json["agribalyse_proxy_food_name"]),
            agribalyseProxyName = LanguageHelper.fromJsonStringMap(json["agribalyse_proxy_name"]),
            carbonFootprintFrFoodgesIngredient = LanguageHelper.fromJsonStringMap(json["carbon_footprint_fr_foodges_ingredient"]),
            children = (json["children"] as List<*>?)?.map { it as String },
            ciqualFoodCode = LanguageHelper.fromJsonStringMap(json["ciqual_food_code"]),
            ciqualFoodName = LanguageHelper.fromJsonStringMap(json["ciqual_food_name"]),
            ciqualProxyFoodCode = LanguageHelper.fromJsonStringMap(json["ciqual_proxy_food_code"]),
            ciqualProxyFoodName = LanguageHelper.fromJsonStringMap(json["ciqual_proxy_food_name"]),
            country = LanguageHelper.fromJsonStringMap(json["country"]),
            grapevariety = LanguageHelper.fromJsonStringMap(json["grapevariety"]),
            instanceof = LanguageHelper.fromJsonStringMap(json["instanceof"]),
            name = LanguageHelper.fromJsonStringMap(json["name"]),
            nova = LanguageHelper.fromJsonStringMap(json["nova"]),
            oqaliFamily = LanguageHelper.fromJsonStringMap(json["oqali_family"]),
            origins = LanguageHelper.fromJsonStringMap(json["origins"]),
            parents = (json["parents"] as List<*>?)?.map { it as String },
            pnnsGroup1 = LanguageHelper.fromJsonStringMap(json["pnns_group_1"]),
            pnnsGroup2 = LanguageHelper.fromJsonStringMap(json["pnns_group_2"]),
            protectedNameFileNumber = LanguageHelper.fromJsonStringMap(json["protected_name_file_number"]),
            protectedNameType = LanguageHelper.fromJsonStringMap(json["protected_name_type"]),
            region = LanguageHelper.fromJsonStringMap(json["region"]),
            seasonInCountryFr = LanguageHelper.fromJsonStringMap(json["season_in_country_fr"]),
            whoId = LanguageHelper.fromJsonStringMap(json["who_id"]),
            wikidata = LanguageHelper.fromJsonStringMap(json["wikidata"]),
            wikidataCategory = LanguageHelper.fromJsonStringMap(json["wikidata_category"]),
            wikidataWikipediaCategory = LanguageHelper.fromJsonStringMap(json["wikidata_wikipedia_category"])
        )
    }

    override fun toString(): String = toJson().toString()
}