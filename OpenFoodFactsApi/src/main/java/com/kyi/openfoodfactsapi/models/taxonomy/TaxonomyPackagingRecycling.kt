package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyPackagingRecyclingField(override val offTag: String) : OffTagged {
    ALL("all"),
    NAME("name"),
    SYNONYMS("synonyms"),
    SHAPE("packaging_shapes"),
    MATERIAL("packaging_materials"),
    CHILDREN("children"),
    PARENTS("parents")
}

class TaxonomyPackagingRecycling : JsonObject() {

    var name: Map<OpenFoodFactsLanguage, String>? = null

    var synonyms: Map<OpenFoodFactsLanguage, List<String>>? = null

    var shape: Map<OpenFoodFactsLanguage, String>? = null

    var material: Map<OpenFoodFactsLanguage, String>? = null

    var children: List<String>? = null

    var parents: List<String>? = null

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyPackagingRecycling = TaxonomyPackagingRecycling().apply {
            name = LanguageHelper.fromJsonStringMap(json["name"])
            synonyms = LanguageHelper.fromJsonStringMapList(json["synonyms"])
            shape = LanguageHelper.fromJsonStringMap(json["shape"])
            material = LanguageHelper.fromJsonStringMap(json["material"])
            children = (json["children"] as List<*>?)?.map { it as String }
            parents = (json["parents"] as List<*>?)?.map { it as String }
        }
    }

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "name" to LanguageHelper.toJsonStringMap(name),
        "synonyms" to LanguageHelper.toJsonStringsListMap(synonyms),
        "packaging_shapes" to LanguageHelper.toJsonStringMap(shape),
        "packaging_materials" to LanguageHelper.toJsonStringMap(material),
        "children" to children,
        "parents" to parents
    )

    override fun toString(): String = toJson().toString()
}