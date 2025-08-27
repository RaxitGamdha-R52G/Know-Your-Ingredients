package com.kyi.openfoodfactsapi.models

import JsonHelper
import com.kyi.openfoodfactsapi.sources.JsonObject


class Ingredient(
    var rank: Int? = null,
    var id: String? = null,
    var text: String? = null,
    var isInTaxonomy: Boolean? = null,
    var percent: Double? = null,
    var percentEstimate: Double? = null,
    var vegan: IngredientSpecialPropertyStatus? = null,
    var vegetarian: IngredientSpecialPropertyStatus? = null,
    var fromPalmOil: IngredientSpecialPropertyStatus? = null,
    var ingredients: List<Ingredient>? = null,
    var bold: Boolean = false
) : JsonObject() {

    override fun toJson(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        if (rank != null) map["rank"] = rank
        if (id != null) map["id"] = id
        map["text"] = text
        if (isInTaxonomy != null) map["is_in_taxonomy"] = isInTaxonomy
        if (percent != null) map["percent"] = percent
        if (percentEstimate != null) map["percent_estimate"] = percentEstimate
        if (vegan != null) map["vegan"] = ingredientSpecialPropertyStatusToJson(vegan)
        if (vegetarian != null) map["vegetarian"] = ingredientSpecialPropertyStatusToJson(vegetarian)
        if (fromPalmOil != null) map["from_palm_oil"] = ingredientSpecialPropertyStatusToJson(fromPalmOil)
        if (ingredients != null) map["ingredients"] = JsonHelper.ingredientsToJson(ingredients)
        map["bold"] = bold
        return map
    }

    companion object {

        private val MAP = mapOf(
            IngredientSpecialPropertyStatus.POSITIVE to "yes",
            IngredientSpecialPropertyStatus.NEGATIVE to "no",
            IngredientSpecialPropertyStatus.MAYBE to "maybe",
            IngredientSpecialPropertyStatus.IGNORE to "ignore"
        )

        fun ingredientSpecialPropertyStatusFromJson(json: Any?): IngredientSpecialPropertyStatus? {
            if (json == null || json !is String) {
                return null
            }
            return MAP.entries.firstOrNull { it.value == json }?.key
        }

        fun ingredientSpecialPropertyStatusToJson(status: IngredientSpecialPropertyStatus?): String? {
            if (status == null) {
                return null
            }
            return MAP[status] ?: throw Exception("New enum type is not handled: $status")
        }

        fun fromJson(json: Map<String, Any?>): Ingredient = Ingredient(
            rank = parseInt(json["rank"]),
            id = json["id"] as String?,
            text = json["text"] as String?,
            isInTaxonomy = parseBool(json["is_in_taxonomy"]),
            percent = parseDouble(json["percent"]),
            percentEstimate = parseDouble(json["percent_estimate"]),
            vegan = ingredientSpecialPropertyStatusFromJson(json["vegan"]),
            vegetarian = ingredientSpecialPropertyStatusFromJson(json["vegetarian"]),
            fromPalmOil = ingredientSpecialPropertyStatusFromJson(json["from_palm_oil"]),
            ingredients = (json["ingredients"] as List<*>?)
                ?.map { fromJson(it as Map<String, Any?>) },
            bold = json["bold"] as Boolean? ?: false
        )
    }

    override fun toString(): String = "Ingredient(" +
            "${if (id == null) "" else "id=$id"}" +
            "${if (rank == null) "" else ",rank=$rank"}" +
            "${if (text == null) "" else ",text=$text"}" +
            "${if (isInTaxonomy == null) "" else ",isInTaxonomy=$isInTaxonomy"}" +
            "${if (percent == null) "" else ",percent=$percent"}" +
            "${if (percentEstimate == null) "" else ",percentEstimate=$percentEstimate"}" +
            "${if (vegan == null) "" else ",vegan=$vegan"}" +
            "${if (vegetarian == null) "" else ",vegetarian=$vegetarian"}" +
            "${if (fromPalmOil == null) "" else ",fromPalmOil=$fromPalmOil"}" +
            "${if (bold == null) "" else ",bold=$bold"}" +
            "${if (ingredients == null) "" else ",ingredients=$ingredients"}" +
            ")"
}