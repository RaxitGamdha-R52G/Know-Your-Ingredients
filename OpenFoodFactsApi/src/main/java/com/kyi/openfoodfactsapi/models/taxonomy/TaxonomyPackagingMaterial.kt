package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyPackagingMaterialField(override val offTag: String) : OffTagged {
    ALL("all"),
    NAME("name"),
    SYNONYMS("synonyms"),
    CHILDREN("children"),
    PARENTS("parents")
}

class TaxonomyPackagingMaterial : JsonObject() {

    var name: Map<OpenFoodFactsLanguage, String>? = null

    var synonyms: Map<OpenFoodFactsLanguage, List<String>>? = null

    var children: List<String>? = null

    var parents: List<String>? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyPackagingMaterial = TaxonomyPackagingMaterial().apply {
            name = LanguageHelper.fromJsonStringMap(json["name"])
            synonyms = LanguageHelper.fromJsonStringMapList(json["synonyms"])
            children = (json["children"] as List<*>?)?.map { it as String }
            parents = (json["parents"] as List<*>?)?.map { it as String }
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "name" to LanguageHelper.toJsonStringMap(name),
        "synonyms" to LanguageHelper.toJsonStringsListMap(synonyms),
        "children" to children,
        "parents" to parents
    )

    override fun toString(): String = toJson().toString()
}