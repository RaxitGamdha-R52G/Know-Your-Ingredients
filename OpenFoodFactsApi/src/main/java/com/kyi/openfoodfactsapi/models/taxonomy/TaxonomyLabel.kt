package com.kyi.openfoodfactsapi.models.taxonomy

import LanguageHelper
import OpenFoodFactsLanguage
import com.kyi.openfoodfactsapi.sources.JsonObject
import com.kyi.openfoodfactsapi.sources.OffTagged

enum class TaxonomyLabelField(override val offTag: String) : OffTagged {
    ALL("all"),
    AUTH_ADDRESS("auth_address"),
    AUTH_NAME("auth_name"),
    AUTH_URL("auth_url"),
    CATEGORIES("categories"),
    CHILDREN("children"),
    COUNTRIES_WHERE_SOLD("countries_where_sold"),
    COUNTRY("country"),
    DESCRIPTION("description"),
    EU_GROUPS("eu_groups"),
    EXCEPTIONS("exceptions"),
    IMAGE("image"),
    IMAGES("images"),
    INGREDIENTS("ingredients"),
    LABEL_CATEGORIES("label_categories"),
    MANUFACTURING_PLACES("manufacturing_places"),
    NAME("name"),
    OPPOSITE("opposite"),
    ORIGINS("origins"),
    PACKAGING("packaging"),
    PACKAGING_PLACES("packaging_places"),
    PARENTS("parents"),
    PROTECTED_NAME_TYPE("protected_name_type"),
    STORES("stores"),
    WIKIDATA("wikidata")
}

class TaxonomyLabel(
    val authAddress: Map<OpenFoodFactsLanguage, String>? = null,
    val authName: Map<OpenFoodFactsLanguage, String>? = null,
    val authUrl: Map<OpenFoodFactsLanguage, String>? = null,
    val categories: Map<OpenFoodFactsLanguage, String>? = null,
    val children: List<String>? = null,
    val countriesWhereSold: Map<OpenFoodFactsLanguage, String>? = null,
    val country: Map<OpenFoodFactsLanguage, String>? = null,
    val description: Map<OpenFoodFactsLanguage, String>? = null,
    val euGroups: Map<OpenFoodFactsLanguage, String>? = null,
    val exceptions: Map<OpenFoodFactsLanguage, String>? = null,
    val image: Map<OpenFoodFactsLanguage, String>? = null,
    val images: Map<OpenFoodFactsLanguage, String>? = null,
    val ingredients: Map<OpenFoodFactsLanguage, String>? = null,
    val labelCategories: Map<OpenFoodFactsLanguage, String>? = null,
    val manufacturingPlaces: Map<OpenFoodFactsLanguage, String>? = null,
    val name: Map<OpenFoodFactsLanguage, String>? = null,
    val opposite: Map<OpenFoodFactsLanguage, String>? = null,
    val origins: Map<OpenFoodFactsLanguage, String>? = null,
    val packaging: Map<OpenFoodFactsLanguage, String>? = null,
    val packagingPlaces: Map<OpenFoodFactsLanguage, String>? = null,
    val parents: List<String>? = null,
    val protectedNameType: Map<OpenFoodFactsLanguage, String>? = null,
    val stores: Map<OpenFoodFactsLanguage, String>? = null,
    val wikidata: Map<OpenFoodFactsLanguage, String>? = null
) : JsonObject() {

    override fun toJson(): Map<String, Any?> = mapOfNotNull(
        "auth_address" to LanguageHelper.toJsonStringMap(authAddress),
        "auth_name" to LanguageHelper.toJsonStringMap(authName),
        "auth_url" to LanguageHelper.toJsonStringMap(authUrl),
        "categories" to LanguageHelper.toJsonStringMap(categories),
        "children" to children,
        "countries_where_sold" to LanguageHelper.toJsonStringMap(countriesWhereSold),
        "country" to LanguageHelper.toJsonStringMap(country),
        "description" to LanguageHelper.toJsonStringMap(description),
        "eu_groups" to LanguageHelper.toJsonStringMap(euGroups),
        "exceptions" to LanguageHelper.toJsonStringMap(exceptions),
        "image" to LanguageHelper.toJsonStringMap(image),
        "images" to LanguageHelper.toJsonStringMap(images),
        "ingredients" to LanguageHelper.toJsonStringMap(ingredients),
        "label_categories" to LanguageHelper.toJsonStringMap(labelCategories),
        "manufacturing_places" to LanguageHelper.toJsonStringMap(manufacturingPlaces),
        "name" to LanguageHelper.toJsonStringMap(name),
        "opposite" to LanguageHelper.toJsonStringMap(opposite),
        "origins" to LanguageHelper.toJsonStringMap(origins),
        "packaging" to LanguageHelper.toJsonStringMap(packaging),
        "packaging_places" to LanguageHelper.toJsonStringMap(packagingPlaces),
        "parents" to parents,
        "protected_name_type" to LanguageHelper.toJsonStringMap(protectedNameType),
        "stores" to LanguageHelper.toJsonStringMap(stores),
        "wikidata" to LanguageHelper.toJsonStringMap(wikidata)
    )

    companion object {

        fun fromJson(json: Map<String, Any?>): TaxonomyLabel = TaxonomyLabel(
            authAddress = LanguageHelper.fromJsonStringMap(json["auth_address"]),
            authName = LanguageHelper.fromJsonStringMap(json["auth_name"]),
            authUrl = LanguageHelper.fromJsonStringMap(json["auth_url"]),
            categories = LanguageHelper.fromJsonStringMap(json["categories"]),
            children = (json["children"] as List<*>?)?.map { it as String },
            countriesWhereSold = LanguageHelper.fromJsonStringMap(json["countries_where_sold"]),
            country = LanguageHelper.fromJsonStringMap(json["country"]),
            description = LanguageHelper.fromJsonStringMap(json["description"]),
            euGroups = LanguageHelper.fromJsonStringMap(json["eu_groups"]),
            exceptions = LanguageHelper.fromJsonStringMap(json["exceptions"]),
            image = LanguageHelper.fromJsonStringMap(json["image"]),
            images = LanguageHelper.fromJsonStringMap(json["images"]),
            ingredients = LanguageHelper.fromJsonStringMap(json["ingredients"]),
            labelCategories = LanguageHelper.fromJsonStringMap(json["label_categories"]),
            manufacturingPlaces = LanguageHelper.fromJsonStringMap(json["manufacturing_places"]),
            name = LanguageHelper.fromJsonStringMap(json["name"]),
            opposite = LanguageHelper.fromJsonStringMap(json["opposite"]),
            origins = LanguageHelper.fromJsonStringMap(json["origins"]),
            packaging = LanguageHelper.fromJsonStringMap(json["packaging"]),
            packagingPlaces = LanguageHelper.fromJsonStringMap(json["packaging_places"]),
            parents = (json["parents"] as List<*>?)?.map { it as String },
            protectedNameType = LanguageHelper.fromJsonStringMap(json["protected_name_type"]),
            stores = LanguageHelper.fromJsonStringMap(json["stores"]),
            wikidata = LanguageHelper.fromJsonStringMap(json["wikidata"])
        )
    }

    override fun toString(): String = toJson().toString()
}